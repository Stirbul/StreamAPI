package com.stirbul.app;

import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.IntStream;

public class Main {

    public static void main(String[] args) {

        User a = new User.Builder("Andrei")
                .balance(100.0)
                .emails(new ArrayList<String>(List.of("adsgsdg","sdgsg","sdgsg")))
                .roles(new ArrayList<String>(List.of("worker, driver")))
                .registrationDate(LocalDateTime.of(2008,1,19,4,30))
                .build();

        User b = new User.Builder("Volodea")
                .balance(99.0)
                .isActive(true)
                .roles(new ArrayList<String>(List.of("worker")))
                .registrationDate(LocalDateTime.of(2008,2,20,5,30))
                .build();

        User c = new User.Builder("Macsik")
                .balance(1000000.0)
                .emails(new ArrayList<String>(List.of("214234","235235","sdg235235sg")))
                .isActive(true)
                .roles(new ArrayList<String>(List.of("driver")))
                .registrationDate(LocalDateTime.of(2007,3,21,6,32))
                .build();

        User d = new User.Builder("Volodea")
                .balance(120.0)
                .emails(new ArrayList<String>(List.of("21sgdg4234","2352dsgsg35","sdg235sd235sg")))
                .isActive(true)
                .roles(new ArrayList<String>(List.of("worker, driver")))
                .registrationDate(LocalDateTime.of(2008,4,22,7,30))
                .build();

        List<User> userList = new ArrayList<>(List.of(a,b,c,d));

        //1) - get sorted list of distinct users
        System.out.println("1");
        System.out.println(userList.stream().filter(distinctByKey(User::getName))
                .sorted(Comparator.comparing(User::getName))
                .collect(Collectors.toList()));
        System.out.println();

        //2)get min, max, avr balance
        System.out.println("2");
        System.out.println("Min = " + userList.stream().mapToDouble(User::getBalance).min()
                .orElseGet(null));
        System.out.println("Max = " + userList.stream().mapToDouble(User::getBalance).max()
                .orElseGet(null));
        System.out.println("Avr = " + userList.stream().mapToDouble(User::getBalance).average()
                .orElseGet(null));
        System.out.println();

        //3) partitioning by active and passive accounts
        System.out.println("3");
        Map<Boolean, List<User>> partitionedUsers = userList.stream()
                .collect(Collectors.partitioningBy(User::isActive));
        partitionedUsers.forEach((Boolean k, List<User> u) -> System.out.println(k + " " + u));
        System.out.println();


        //4) get list of emails
        // parallel() for using not the first User
        System.out.println("4");
        System.out.println(userList.stream().parallel()
                .map(User::getEmails)
                .collect(Collectors.toList())
        );
        System.out.println();

        //5) Group Users by registration Date
        System.out.println("5");
        System.out.println(userList.stream().collect(Collectors.groupingBy(User::getRegistrationDate)));
        System.out.println();

        //6)group Users by their roles(Map<Role, List<User>>)
        System.out.println("6");
        Map<String, List<String>> map =
                new HashMap<>();
        for (User u : userList){
            List<String> list = new ArrayList<>();
            for(String role : u.getRoles()){
                if (u.getRoles().contains(role)) {
                    list.add(u.getName());
                }
                map.putIfAbsent(role,list);
            }
        }
        System.out.println(map);
        System.out.println();

        //7)Get Set from List
        System.out.println("7");
//        Set<User> set =
                userList.stream().filter(distinctByKey(User::getName)).collect(Collectors.toSet())
                .forEach(user -> System.out.print(user.getName() + " "));
//        System.out.println(set);
        System.out.println("\n");

        //8)Number of non active Users
        System.out.println("8");
        System.out.println("Number of non active Users : " +
//                userList.stream().filter(user -> !user.isActive()).count());
                userList.stream().filter(Predicate.not(User::isActive)).count());
        System.out.println();

        //9) Get first user with balance > 10000 and what if there is no such
        System.out.println("9");
        System.out.println(userList.stream()
                .filter(u -> u.getBalance() > 10000).findFirst().orElse(null));
        System.out.println();

        //10) Reduce all users names into 1 coma separated String
        System.out.println("10");
        System.out.println(userList.stream()
                .map(User::getName)
                .collect(Collectors.joining(","))
        );
        System.out.println();

        System.out.println("Cartesian Pruduct \n\n");
        List<Integer> ints = new ArrayList<>(List.of(1,2,3));
        List<Character> chars = new ArrayList<>(List.of('x','y','z'));

        List<String> cartesian = new ArrayList<>();
        ints.forEach(i -> chars.forEach(z -> cartesian.add(z + String.valueOf(i))));
        System.out.println(cartesian);

        int count = ints.size();
        for (int i = 0; i < cartesian.size(); ++i) {
                System.out.print(cartesian.get(i) + " ");
            if (count == i+1) {
                count *= 2;
                System.out.println();
            }
        }

        System.out.println("\n\nFactorial");
        int n = 5;
        System.out.println(IntStream.rangeClosed(2,n).reduce(1, (g,h) -> g*h));



    }

    private static <T> Predicate<T> distinctByKey(Function<? super T, ?> keyExtractor) {
        Map<Object,Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }
}
