import functions.*;

public class Main
{
    public static void main(String[] args)
    {
        System.out.println("ТЕСТИРОВАНИЕ ТАБУЛИРОВАННЫХ ФУНКЦИЙ");

        // Тестирование функции y = x²
        System.out.println("Квадратичная функция y = x²");
        TabulatedFunction func = new ArrayTabulatedFunction(0, 4, 5);

        for (int i = 0; i < func.getPointsCount(); i++)
        {
            double x = func.getPointX(i);
            func.setPointY(i, x * x);
        }

        printFunction("Исходная функция", func);
        System.out.println();

        // Добавление точки
        System.out.println("Добавление точки (2.5, 6.25)");
        try
        {
            func.addPoint(new FunctionPoint(2.5, 6.25));
        } catch (InappropriateFunctionPointException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
        printFunction("После добавления", func);
        System.out.println();

        // Удаление точки
        System.out.println("Удаление точки с индексом 2");
        try
        {
            func.deletePoint(2);
        } catch (Exception e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
        printFunction("После удаления", func);
        System.out.println();

        // Изменение точки
        System.out.println("Изменение точки с индексом 1");
        try
        {
            func.setPoint(1, new FunctionPoint(1.5, 2.25));
        } catch (InappropriateFunctionPointException e)
        {
            System.out.println("Ошибка: " + e.getMessage());
        }
        printFunction("После изменения", func);
        System.out.println();

        // Интерполяция
        System.out.println("Интерполяция значений");
        testInterpolation(func);
        System.out.println();

        // Границы области определения
        System.out.println("Границы области определения");
        System.out.println("Левая граница: " + func.getLeftDomainBorder());
        System.out.println("Правая граница: " + func.getRightDomainBorder());
        System.out.println();

        // Тестирование исключений
        System.out.println("ТЕСТИРОВАНИЕ ИСКЛЮЧЕНИЙ");
        testExceptions();
        System.out.println();

        // Сравнение реализаций
        System.out.println("СРАВНЕНИЕ РЕАЛИЗАЦИЙ");
        compareImplementations();
    }

    private static void printFunction(String title, TabulatedFunction func)
    {
        System.out.println(title + ":");

        for (int i = 0; i < func.getPointsCount(); i++)
        {
            try
            {
                System.out.println("  Точка " + i + ": x=" + func.getPointX(i) + ", y=" + func.getPointY(i));
            } catch (FunctionPointIndexOutOfBoundsException e)
            {
                System.out.println("  Ошибка доступа к точке " + i);
            }
        }

        System.out.println("Всего точек: " + func.getPointsCount());
    }

    private static void testInterpolation(TabulatedFunction func)
    {
        double[] testPoints = {0.5, 1.2, 2.8, 3.5};

        for (double x : testPoints)
        {
            double y = func.getFunctionValue(x);
            System.out.println("  f(" + x + ") = " + y);
        }

        System.out.println("  f(-1) = " + func.getFunctionValue(-1) + " (слева от области)");
        System.out.println("  f(5) = " + func.getFunctionValue(5) + " (справа от области)");
    }

    private static void testExceptions()
    {
        // Некорректный конструктор
        System.out.println("Тест конструкторов:");

        try
        {
            new ArrayTabulatedFunction(5, 3, 4);
            System.out.println("  Ошибка: исключение не было выброшено");
        } catch (IllegalArgumentException e) {
            System.out.println("  Поймано: " + e.getMessage());
        }
        System.out.println();

        // Выход за границы индекса
        System.out.println("Тест индексных ошибок:");
        TabulatedFunction testFunc = new ArrayTabulatedFunction(0, 2, 3);
        try
        {
            testFunc.getPoint(10);
            System.out.println("  Ошибка: исключение не было выброшено");
        } catch (FunctionPointIndexOutOfBoundsException e)
        {
            System.out.println("  Поймано: " + e.getMessage());
        }
        System.out.println();

        // Нарушение упорядоченности
        System.out.println("Тест упорядоченности:");
        try
        {
            testFunc.setPoint(1, new FunctionPoint(5, 10));
            System.out.println("  Ошибка: исключение не было выброшено");
        } catch (InappropriateFunctionPointException e)
        {
            System.out.println("  Поймано: " + e.getMessage());
        }
        System.out.println();

        // Добавление точки с существующим X
        System.out.println("Тест дублирования точек:");
        try
        {
            testFunc.addPoint(new FunctionPoint(1.0, 5.0));
            System.out.println("  Ошибка: исключение не было выброшено");
        } catch (InappropriateFunctionPointException e)
        {
            System.out.println("  Поймано: " + e.getMessage());
        }
        System.out.println();

        // Удаление при малом количестве точек
        System.out.println("Тест удаления:");
        try
        {
            testFunc.deletePoint(0);
            testFunc.deletePoint(0);
            testFunc.deletePoint(0);
            System.out.println("  Ошибка: исключение не было выброшено");
        } catch (IllegalStateException e)
        {
            System.out.println("  Поймано: " + e.getMessage());
        }
    }

    private static void compareImplementations()
    {
        double[] values = {0, 1, 4, 9, 16};

        TabulatedFunction arrayFunc = new ArrayTabulatedFunction(0, 4, values);
        TabulatedFunction listFunc = new LinkedListTabulatedFunction(0, 4, values);

        printFunction("ArrayTabulatedFunction", arrayFunc);
        System.out.println();

        printFunction("LinkedListTabulatedFunction", listFunc);
        System.out.println();

        System.out.println("Сравнение интерполяции:");
        double[] testX = {0.5, 1.5, 2.5, 3.5};

        for (double x : testX)
        {
            double arrayY = arrayFunc.getFunctionValue(x);
            double listY = listFunc.getFunctionValue(x);
            System.out.println("  f(" + x + "): array=" + arrayY + ", list=" + listY);
        }
    }
}