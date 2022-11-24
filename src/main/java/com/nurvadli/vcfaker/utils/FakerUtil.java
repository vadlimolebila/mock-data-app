package com.nurvadli.vcfaker.utils;

import com.github.javafaker.Faker;
import com.nurvadli.vcfaker.entity.Gender;
import com.nurvadli.vcfaker.entity.MaritalStatus;

import java.security.SecureRandom;
import java.util.Locale;

public final class FakerUtil {

    public static String LOCAL_ID = "in-ID";

    private static final SecureRandom random = new SecureRandom();

    public static Faker init() {
        return new Faker(new Locale(LOCAL_ID));
    }

    public static String getName() {
        return init().name().name();
    }

    public static String getName(Faker faker) {
        return faker.name().name();
    }

    public static String getFirstName() {
        return init().name().firstName();
    }

    public static String getLastName() {
        return init().name().lastName();
    }

    public static String getMobilePhone() {
        return init().phoneNumber().phoneNumber();
    }

    public static String getEmail() {
        return init().internet().emailAddress();
    }

    public static String getEmail(Faker faker) {
        return faker.internet().emailAddress();
    }

    public static String getGender() {
        return randomEnum(Gender.class).name();
    }

    public static String getMaritalStatus() {
        return randomEnum(MaritalStatus.class).name();
    }

    public static String getAddress() {
        return init().address().streetAddress();
    }
    public static String getAddress(Faker faker) {
        return faker.address().streetAddress();
    }

    public static String getDateOfBirth() {
        return init().date().birthday().toString();
    }

    public static String getDateOfBirth(Faker faker) {
        return faker.date().birthday().toString();
    }

    public static String getPlaceOfBirth() {
        return init().address().cityName();
    }

    public static String getPlaceOfBirth(Faker faker) {
        return faker.address().cityName();
    }

    public static String getIdNumber() {
        return init().idNumber().invalid();
    }

    public static String getIdUnique() {
        return init().idNumber().ssnValid();
    }

    private static  <T extends Enum<?>> T randomEnum(Class<T> clazz) {
        int x = random.nextInt(clazz.getEnumConstants().length);
        return clazz.getEnumConstants()[x];
    }

}
