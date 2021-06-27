package org.jalvarova.currency;

public class Main {


    public static void main(String[] args) {

        int[] array = new int[]{1, 2, 8, 23, 5, 15, 17, 15};

        int[] newArray = new int[array.length];
        for (int i = 0; i < array.length; i++) {
            int number = array[i];
            if (isPrimo(number)){
                newArray[i] = number;
            }
        }

        int max;
        for (int i = 0; i < newArray.length; i++) {
            for (int j = 0; j < newArray.length; j++) {
                if (newArray[j] > newArray[i]) {
                    max = newArray[i];
                    newArray[i] = newArray[j];
                    newArray[j] = max;
                }
            }
        }

        for (int i = 0; i < newArray.length; i++) {
            System.out.println(newArray[i]);
        }
    }

    private static boolean isPrimo(int num){
        boolean primo = num != 1;
        int divisor=2;
        while(primo && divisor<=Math.sqrt(num)){
            if(num%divisor==0){
                primo = false;
            }
            divisor++;
        }
        return primo;
    }

}
