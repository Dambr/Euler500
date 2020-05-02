import java.math.BigInteger;
import java.util.ArrayList;


public class Euler500 {
    /**
     * Функция возведения большого числа в степень
     *
     * @param x - число, которое необходимо возвести в степень
     * @param n - значение степени
     * @return результат работы функции. Число типа BigInteger
     */
    static BigInteger bigPow(BigInteger x, BigInteger n) {
        BigInteger res = BigInteger.valueOf(1);
        BigInteger i = BigInteger.valueOf(0);
        while (i.compareTo(n) < 0) {
            res = res.multiply(x);
            i = i.add(BigInteger.valueOf(1));
        }
        return res;
    }

    /**
     * Функция определения, является ли число простым
     *
     * @param x - число, которое необходимо проверить
     * @return логическое true или false в зависимости от того,
     * является ли число, поданое на вход, простым либо нет
     */
    static boolean isPresent(BigInteger x) {
        BigInteger i = BigInteger.valueOf(2);
        while (i.compareTo(x.sqrt()) <= 0) {
            if (x.mod(i).compareTo(BigInteger.valueOf(0)) == 0) {
                return false;
            }
            i = i.add(BigInteger.valueOf(1));
        }
        return true;
    }

    /**
     * Функция получения следующего простого числа
     *
     * @param x - число, следующее простое от которого необходимо определить
     * @return число типа BigInteger, которое является простым
     */
    static BigInteger getNextPresent(BigInteger x) {
        BigInteger i = BigInteger.valueOf(1);
        while (true) {
            if (isPresent(x.add(i))) {
                return x.add(i);
            }
            i = i.add(BigInteger.valueOf(1));
        }
    }

    static BigInteger getAnswer(int n) {
        ArrayList<BigInteger> pows = new ArrayList<>();
        ArrayList<BigInteger> presents = new ArrayList<>();
        pows.add(BigInteger.valueOf(2));
        presents.add(BigInteger.valueOf(2));
        for (int i = 1; i < n; i++) {
            pows.add(BigInteger.valueOf(2));
            presents.add(getNextPresent(presents.get(presents.size() - 1)));
        }
        for (int i = 0; i < presents.size(); ) {
            // Простое i-е число
            BigInteger Pi = presents.get(i);
            // Последний элемент списка простых числел
            BigInteger Psize = presents.get(presents.size() - 1);
            // i-я степень
            BigInteger Ni = pows.get(i);
            // Последний элемент списка степеней
            BigInteger Nsize = pows.get(pows.size() - 1);
            // левая часть неравенства
            BigInteger left = bigPow(Pi, Ni.multiply(Nsize).subtract(BigInteger.valueOf(1)));
            // правая чать неравенства 1
            BigInteger right1 = bigPow(Pi, Ni.subtract(BigInteger.valueOf(1)));
            // правая часть неравенства 2
            BigInteger right2 = bigPow(Psize, Nsize.subtract(BigInteger.valueOf(1)));
            // Правая часть неравенства (вся)
            BigInteger right = right1.multiply(right2);
            if (left.compareTo(right) < 0) {
                pows.set(i, pows.get(i).multiply(pows.get(pows.size() - 1)));
                pows.remove(pows.size() - 1);
                presents.remove(presents.size() - 1);
                i = 0;
            } else {
                i++;
            }
        }
        BigInteger answer = BigInteger.valueOf(1);
        for (int i = 0; i < presents.size(); i++) {
            answer = answer.multiply(bigPow(presents.get(i), pows.get(i).subtract(BigInteger.valueOf(1))));
        }
        return answer;
    }

    public static void main(String[] args) {
        long time = System.currentTimeMillis();
        System.out.println("[+]---Минимальное число для степени 4:  " + getAnswer(4));
        System.out.println("[+]---Время выполнения для степени 4:  " + (System.currentTimeMillis() - time) / 1000 + "с");
        System.out.println();
        time = System.currentTimeMillis();
        BigInteger answer = getAnswer(500500);
        System.out.println("[+]---В результирующем числе " + answer.toString().length() + " знаков");
        System.out.println("[+]---Ответ =>  " + answer.mod(BigInteger.valueOf(500500507L)));
        System.out.println("[+]---Время рассчета ответа:  " + (System.currentTimeMillis() - time) / 1000 + "c");
    }
}