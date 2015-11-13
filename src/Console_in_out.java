import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Created by y on 03.09.2015.
 */
// TODO: исключить появление NaN в выводе на экран.
// TODO: исключить возможность ввода кириллических символов или расширить контекст.
public class Console_in_out {

// поля объектов геометрических фигур
    private static Line line1,line2;
    private static Circle circle1,circle2;
    private static Triangle triangle1,triangle2;
    private static Rectangle rectangle1,rectangle2;
// вспомогательные поля методов
    private static int i=0;
    private static String s = "";
    private static String inters = "";
    private static boolean b,b1,b2,b3 = false;

//---------------------управление-----------------
    // точка входа
    public static void main(String[] args) throws IOException {
       programmRun();
    }

    // метод управления программой
    static void programmRun() throws IOException {
        System.out.println("Программа находит точки пересечения между простыми геометрическими фигурами");

        while(!s.equals("E") | !s.equals("e")) {
            System.out.println("Запустить программу нажмите любую клавишу");
            System.out.println("Выйти из программы введите E");
            s = input_str(); if(s.equals("E") | s.equals("e")) break;
            for ( i = 0; i < 2 ; i++) {
                System.out.println("Выбор фигуры: l - прямая линия, c - круг, t - треугольник, r - прямоугольник");
                s = input_str(); setPoints(s);
            }
            figureIntersection(inters);  inters = ""; b=b3=b2=b3=false;
        }
    }

//---------------------установка параметров и вычисление------------
    // метод устанавливает параметры геометрических фигур, т.е. соответствующие поля объектов классов.
    static void setPoints(String s){
        inters += s;
        Double x1,y1,x2,y2,x3,y3,r;
        if(s.equals("l")){
            Line line = new Line();
            System.out.println("прямая линия");
            System.out.println("Введите x1");x1 =input_double();
            System.out.println("Введите y1");y1 =input_double();
            System.out.println("Введите x2");x2 =input_double();
            System.out.println("Введите y2");y2 =input_double();
            line.set_points(x1,y1,x2,y2);
            if(i==0){line1 = line; b = true;}
            if(i==1) {if(b) line2 = line; if(!b){ line1 = line;}}}
        if(s.equals("c")){ Circle circle = new Circle();
            System.out.println("круг");
            System.out.println("Введите координату центра x");x1 =input_double();
            System.out.println("Введите координату центра y");y1 =input_double();
            // исключение ввода отрицательного значения радиуса.-------------
            r=0.0;
            while (true){
                System.out.println("Введите значение величины радиуса");
                r =input_double();
                if(r>0){break;} else {System.out.println
                        ("Значение радиуса не может быть отрицательным числом или нулем!");}
                }
            //-----------------------
            circle.set_points(r,x1,y1);
            if(i==0){circle1 = circle; b1 = true;}
            if(i==1) {if(b1) circle2 = circle; if(!b1){ circle1 = circle;}}}
        if(s.equals("t")){ Triangle triangle = new Triangle();
            System.out.println("треугольник");
            System.out.println("Введите x1");x1 =input_double();
            System.out.println("Введите y1");y1 =input_double();
            System.out.println("Введите x2");x2 =input_double();
            System.out.println("Введите y2");y2 =input_double();
            System.out.println("Введите x3");x3 =input_double();
            System.out.println("Введите y3");y3 =input_double();
            triangle.set_points(x1,y1,x2,y2,x3,y3);
            if(i==0){triangle1 = triangle; b2 = true;}
            if(i==1) {if(b2) triangle2 = triangle; if(!b2){ triangle1 = triangle;}}}
        if(s.equals("r")){ Rectangle rectangle = new Rectangle();
            System.out.println("прямоугольник: координаты диагонали");
            System.out.println("Введите x1");x1 =input_double();
            System.out.println("Введите y1");y1 =input_double();
            System.out.println("Введите x2");x2 =input_double();
            System.out.println("Введите y2");y2 =input_double();
            rectangle.setPoints(x1,y1,x2,y2);
            if(i==0){rectangle1 = rectangle; b3 = true;}
            if(i==1) {if(b3) rectangle2= rectangle; if(!b3){ rectangle1 = rectangle;}}}
    }

    // метод вычисляет точки пересечения фигур по контексту строки s.
    static void figureIntersection(String s){
      if(s.equals("ll")){Object[] ob  = line1.intersection(line2);
         toScreen(ob); }
      if(s.equals("lc") | s.equals("cl")){ Object[] ob  = circle1.intersection_with_line(line1);
         toScreen(ob); }
      if(s.equals("lt") | s.equals("tl")){ Object[][] ob = triangle1.trianle_intersection_with_line(line1);
         toScreen(ob);}
      if(s.equals("lr") | s.equals("rl")){ Object[] ob = rectangle1.intrsc_Line(line1);
         toScreen(ob);}

      if(s.equals("cc")){Object[] ob = circle1.circles_intersection(circle2);
         toScreen(ob);}
      if(s.equals("ct") | s.equals("tc")){ Object[][]ob = triangle1.trianle_intersection_with_circle(circle1);
         toScreen(ob);}
      if(s.equals("cr") | s.equals("rc")){Object[][]ob = rectangle1.intrsc_Rect_Circle(circle1);
         toScreen(ob);}

      if(s.equals("tt")){Object[][]ob = triangle1.trianles_intersection(triangle2);
         toScreen(ob);}
      if(s.equals("tr") | s.equals("rt")){ Object[][][]ob = rectangle1.intrsc_Rect_Triangle(triangle1);
         toScreen(ob);}

      if(s.equals("rr")){Object[][]ob = rectangle1.intrsc_Rect_Rect(rectangle2);
         toScreen(ob);}


  }


//---------------------вывод на экран--------------
    // переполненый метод выводит на экран точки пересечения фигур.
    static void toScreen(Object[]ob){
        int count = 0;
        boolean b;
        for(int i = 0; i<ob.length; i++) {
            b = !ob[i].equals(-0.0) & !ob[i].equals(true) & !ob[i].equals(false);
            if(b)
            {System.out.println(ob[i]);}
            if(!b){count++;}
        }
        if(count == ob.length){System.out.println("Точек пересечения нет"); }
    }

    static void toScreen(Object[][]ob){
        int count = 0;
        boolean b;
        for(int i = 0; i<ob.length; i++) { for(int k = 0; k<ob[0].length; k++){
            b = !ob[i][k].equals(-0.0) & !ob[i][k].equals(true) & !ob[i][k].equals(false);
            if(b)
            {System.out.println(ob[i][k]);}
            if(!b){count++;}
        }}
        if(count == ob.length*ob[0].length){System.out.println("Точек пересечения нет"); }
    }

    static void toScreen(Object[][][]ob){
        int count = 0;
        boolean b;
        for(int i = 0; i<ob.length; i++) { for(int k = 0; k<ob[0].length; k++){ for(int j = 0; j<ob[0][0].length; j++){
            b = !ob[i][k][j].equals(-0.0) & !ob[i][k][j].equals(true) & !ob[i][k][j].equals(false);
            if(b)
            {System.out.println(ob[i][k][j]);}
            if(!b){count++;}
        }}}
        if(count == ob.length*ob[0].length*ob[0][0].length){System.out.println("Точек пересечения нет"); }
    }


//---------------------вспомогательные функции-----
    // метод ввода числа, возвращает введенное в консоли число и перехватывает исключение ввода другого символа.
    public static Double input_double() {
        while (true) {
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                return Double.parseDouble(reader.readLine());
            } catch (Exception e) {
                System.out.println("not a number, try again");
            }
        }
    }

    // метод ввода,возвращает строку введеную через консоль
    public static String input_str() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
