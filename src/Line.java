import static java.lang.Math.abs;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;

/*
    Класс взаимосвязан с подклассами наследованием(class Circle)
     и агрегацией(class Triangle, class Rectangle).
     Подклассы взаимосвязаны агрегацией.
     */
public class Line {

  // функция и аргумент
     Double function;
     Double x;
  // координаты точек
    private Double x1;
    private Double y1;
    private Double x2;
    private Double y2;
 // значение радиуса и координаты центра
     Double r;
     Double x_center;
     Double y_center;
 // параметры a, b линейной функции f = ax + b
     Double a;
     Double b;
    Boolean isx_const = false;

 // метод устанавливает координаты двух точек
    public void set_points (Double x1,Double y1,Double x2, Double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        function_definition(1.0);
    }

 // медод задущий параметры a b и функцию по точкам с координатами x1 y1 x2 y2
    public Double function_definition(Double x){
        this.x = x;
        a = (y1 - y2)/(x1-x2);  if(x1.equals(x2)) { this.x = x1; a = 0.0; isx_const = true; } // случай деления на ноль, возможно будет приводить к ошибкам вычисления
        b = y1 - a*x1;
        function = a*this.x +b;
        return function;
    }

 // метод определяющий принадлежит ли определенная точка линии
     boolean membership_point(Double x, Double y){
         boolean b = false;
        if (!isx_const & function_definition(x).equals(y) ) b = true;
        if (isx_const & this.x.equals(x)) b = true;
         return b;
    }

 // метод определяет пересекаются ли линии и точку пересечения
     Object[] intersection(Line primitives){ // тестить метод!
        Object[] objects = new Object[3];
        Double x = (primitives.b-b)/(a -primitives.a);
        Double y = x*a +b;
        if(isx_const){x = this.x; y = primitives.function_definition(x); }
        if(primitives.isx_const){x = primitives.x; y = function_definition(x); }
            objects[0] = true;
            objects[1] = x;
            objects[2] = y;
        if(primitives.isx_const & isx_const){ objects[0] = false; objects[1] =  objects[2] = -0.0; }
        if((Double.isInfinite(x)| Double.isNaN(x)) & (Double.isInfinite(y)| Double.isNaN(y))){
              objects[0] = false; objects[1] =  objects[2] = -0.0;
        }
            return objects;
    }

}

class Circle extends Line {

    Double function1;

    public void set_points(Double r,Double x_center,Double y_center){
        this.r = abs(r); this.x_center = x_center; this.y_center = y_center;
        function_definition_c(1.0);
    }

    public Object[] function_definition_c(Double x){
        this.x = x;
        Object[] d = new Object[3];
        function = y_center + sqrt(pow(r,2.0) - pow((x-x_center),2));  // функция круга состоит из двух функций
        function1 = y_center - sqrt(pow(r,2.0) - pow((x-x_center),2));
        d[0] = true;                      // показывает определена ли функция в данной точке х
        d[1] = function;
        d[2] = function1;
        if (Double.isNaN(function)) d[0] = false;
        return d;
    }

    public boolean membership_point(Double x, Double y){
        Object[] f = function_definition_c(x);
        Object f1 = f[1];
        Object f2 = f[2];
        if(!(boolean)f[0]) System.out.println("the point is outside the range of values");
        if (f1.equals(y)| f2.equals(y)) return true;
        else return false;
    }

    public Object[] circles_intersection(Circle circle){  // пересечение окружностей
        Object[] intersect = new Object[5]; intersect[0] = false; intersect[1] = -0.0; intersect[2] = -0.0; intersect[3] = -0.0; intersect[4] = -0.0;
        Double d = sqrt(pow((y_center - circle.y_center),2.0)+ pow((x_center - circle.x_center),2.0));  // расстояние межу центрами окружностей
        if(r+circle.r < d | abs(r - circle.r) > d) {System.out.println("circle do not intersect"); return intersect ;}   // условия при которых окружности не пересекаются
        Double b = (pow(circle.r,2.0) - pow(r,2.0) + pow(d,2.0))/(2*d);     // а и b это длины отрезков от центров окружностей к перпендикуляру проходящему через точки пересечения окр.
        Double a = d - b;
        Double h = sqrt(pow(r,2.0) - pow(a,2.0));
         // Найдем точку P0 с помощью векторного параметрического уравнения прямой:
        Double px = ((a/d) * (circle.x_center - x_center)) + x_center;
        Double py = ((a/d) * (circle.y_center - y_center)) + y_center;
        // Зная координаты точки P0 находим координаты точек P3 и P4. Для этого возмем единичный вектор от P1 до P2,
        // повернем его на +90 градусов для P3 (-90 градусов для P4) и умножим на h.                http://www.litunovskiy.com/gamedev/intersection_of_two_circles/
        Double p1x = px + ((circle.y_center - y_center)/d)*h;
        Double p1y = py - ((circle.x_center - x_center)/d)*h;
        Double p2x = px - ((circle.y_center - y_center)/d)*h;
        Double p2y = py + ((circle.x_center - x_center)/d)*h;
        intersect[0] = true;  intersect[1] = p1x; intersect[2] = p1y; intersect[3] = p2x; intersect[4] = p2y;
        return intersect;
    }

    public Object[] intersection_with_line(Line line){
        Object[] ob = new Object[5];
        Double C = (2.0*y_center*line.b - pow(x_center,2.0) + pow(r,2.0) - pow(line.b,2.0) - pow(y_center,2.0))*(-1.0);
        Double A = 1.0 + pow(line.a,2.0);
        Double B = -2.0*x_center + 2.0*line.a*line.b - 2.0*y_center*line.a;
        Double D = pow(B,2.0) - 4.0*A*C;
        Double x1 = (-B + sqrt(D))/2.0*A;
        Double x2 = (-B - sqrt(D))/2.0*A;
        Double y1 = line.function_definition(x1);
        Double y2 = line.function_definition(x2);
        if(Double.isNaN(y1) & Double.isNaN(y2)) {ob[0] = false; ob[1] = ob[2] = ob[3] = ob[4] = -0.0; return ob;  }
        else {
            ob[0] = true; ob[1] = x1; ob[2] = y1; ob[3] = x2; ob[4] = y2;  return ob; // выходные данные
        }
    }
}

class Triangle {
    private Double x1,y1,x2,y2,x3,y3;
   private Line line1,line2,line3;

    public void set_points(Double x1,Double y1,Double x2, Double y2,Double x3,Double y3){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.x3 = x3;
        this.y3 = y3;
        line1 = new Line();  line1.set_points(x1,y1,x2,y2);
        line2 = new Line();  line2.set_points(x1,y1,x3,y3);
        line3 = new Line();  line3.set_points(x2,y2,x3,y3);

    }

    public boolean membership_point1(Double x,Double y){
        boolean b = false;
        boolean b1 = line1.membership_point(x,y);
        if(b1){
        if(y1>=y2){if(y >= y2 & y <= y1) b = true;}
        if(y2>=y1){if(y >= y1 & y <= y2) b = true;}
        if(y1.equals(y2)){
            if(x1>=x2){
                b = x >= x2 & x <= x1;
            }
            if(x2>=x1){
                b = x >= x1 & x <= x2;
            }}
        }
        return b;
    }

    public boolean membership_point2(Double x,Double y){
        boolean b = false;
        boolean b1 = line2.membership_point(x,y);
        if(b1){
        if(y1>=y3){if(y >= y3 & y <= y1) b = true;}
        if(y3>=y1){if(y >= y1 & y <= y3) b = true;}
            if(y1.equals(y3)){
                if(x1>=x3){
                    b = x >= x3 & x <= x1;
                }
                if(x3>=x1){
                    b = x >= x1 & x <= x3;
                }}
        }
        return b;
    }

    public boolean membership_point3(Double x,Double y){
        boolean b = false;
        boolean b1 = line3.membership_point(x,y);
        if(b1){
        if(y3>=y2){if(y >= y2 & y <= y3) b = true;}
        if(y2>=y3){if(y >= y3 & y <= y2) b = true;}
            if(y3.equals(y2)){
                if(x3>=x2){
                    b = x >= x2 & x <= x3;
                }
                if(x2>=x3){
                    b = x >= x3 & x <= x2;
                }}
        }
        return b;
    }

    public boolean membership_point(Double x,Double y){
        if (membership_point1(x,y) | membership_point2(x,y) | membership_point3(x,y)) return true;
        else return false;
    }


    public Object[][] trianle_intersection_with_circle(Circle circle){
        Object[][] triangle_w_circle = new Object[3][5];   // содержание строки: 0- пересекает ли окр. сторону треуг.; 1- x1; 2- y1; 3- x2; 4- y2;
        for (int i = 0; i < 3; i++)
        {for (int k = 0; k < 5; k++) { if(k==0) triangle_w_circle[i][k] = false; else triangle_w_circle[i][k] = -0.0; }}  // заполнение массива пустыми значениями
          Line line = new Line();
        for(int l = 0; l < 3; l++){
            if(l == 0) { line = line1;}
            if(l == 1) { line = line2;}
            if(l == 2) { line = line3;}
        Object[]circle_w_line = circle.intersection_with_line(line);
        boolean membership_p1 = membership_point((Double)circle_w_line[1],(Double)circle_w_line[2]);
        boolean membership_p2 = membership_point((Double)circle_w_line[3],(Double)circle_w_line[4]);
        boolean is_intrs = (boolean)circle_w_line[0];
        if(is_intrs & membership_p1){for ( int i = 0; i < 3; i++){ triangle_w_circle[l][i] = circle_w_line[i]; }}
        if(is_intrs & membership_p2){for (int i = 3; i < 5; i++){ triangle_w_circle[l][i] = circle_w_line[i];}}
        if((is_intrs & membership_p1) | (is_intrs & membership_p2)){triangle_w_circle[l][0] = true;}
         }
        return triangle_w_circle;
    }

    public Object[][] trianle_intersection_with_line(Line line){
        Object[][] ob = new Object[3][3];   ob[0][0]=false;
          Object[] ob1 = new Object[3];
          Object[] ob2 = new Object[3];
          Object[] ob3 = new Object[3];
          ob1[0] = ob2[0] = ob3[0] = false;    for (int i = 1; i < 3; i++) { ob1[i] = ob2[i] = ob3[i] = -0.0;  }   // заполнение массива пустыми значениями

          Object[] intrs1 = line1.intersection(line);  boolean b1 = (boolean)intrs1[0];   Double x1 = (Double)intrs1[1];   Double y1 = (Double)intrs1[2];
          Object[] intrs2 = line2.intersection(line);  boolean b2 = (boolean)intrs2[0];   Double x2 = (Double)intrs2[1];   Double y2 = (Double)intrs2[2];
          Object[] intrs3 = line3.intersection(line);  boolean b3 = (boolean)intrs3[0];   Double x3 = (Double)intrs3[1];   Double y3 = (Double)intrs3[2];
         if(b1 & membership_point1(x1,y1))            {ob1[0] = true; ob1[1] = x1; ob1[2] = y1; }
         if(b2 & membership_point2(x2,y2))            {ob2[0] = true; ob2[1] = x2; ob2[2] = y2; }
         if(b3 & membership_point3(x3,y3))            {ob3[0] = true; ob3[1] = x3; ob3[2] = y3; }
            ob[0][0] = ob1[0];
           ob[0][1] = ob1[1];
           ob[0][2] = ob1[2];
        ob[1][0] = ob2[0];
        ob[1][1] = ob2[1];
        ob[1][2] = ob2[2];
           ob[2][0] = ob3[0];
           ob[2][1] = ob3[1];
           ob[2][2] = ob3[2];

    return ob;
    }

    public Object[][] trianles_intersection(Triangle triangle){
          Object[][] ob0 = new Object[9][3];   for(int i =0; i < 9; i++){for(int k = 0; k < 3; k++){ if(k == 0 ) ob0[i][k] = false; else ob0[i][k] = -0.0; }}
        Object[][] ob1 = trianle_intersection_with_line(triangle.line1);
             for (int i = 0; i < 3; i++){for (int k = 0; k <3; k++){ if(triangle.membership_point((Double)ob1[i][1],(Double)ob1[i][2]))  ob0[i][k] = ob1[i][k]; }}
        Object[][] ob2 = trianle_intersection_with_line(triangle.line2);
             for (int i = 3; i < 6; i++){for (int k = 0; k <3; k++){ if(triangle.membership_point((Double)ob2[i-3][1],(Double)ob2[i-3][2])) ob0[i][k] = ob2[i-3][k]; }}
        Object[][] ob3 = trianle_intersection_with_line(triangle.line3);
             for (int i = 6; i < 9; i++){for (int k = 0; k <3; k++){ if(triangle.membership_point((Double)ob3[i-6][1],(Double)ob3[i-6][2])) ob0[i][k] = ob3[i-6][k]; }}
        return ob0;
    }
}

class Rectangle{
    private Double x1,y1,x2,y2;
    private Line line1,line2,line3,line4;

    public void setPoints(Double x1, Double y1, Double x2,Double y2){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        createRect();
    }

    private void createRect(){
       line1 = new Line(); line1.set_points(x1,y1,x1,y2);  // вертикальная линия
       line2 = new Line(); line2.set_points(x2,y2,x2,y1);  // вертикальная линия
       line3 = new Line(); line3.set_points(x1,y2,x2,y2);  // горизонтальная линия
       line4 = new Line(); line4.set_points(x1,y1,x2,y1);  // горизонтальная линия
    }

    private boolean membership_point1(Double x,Double y){
        boolean b = false;
        boolean b1 = line1.membership_point(x,y);
        if(b1){
            if(y1>=y2){if(y >= y2 & y <= y1) b = true;}
            if(y2>=y1){if(y >= y1 & y <= y2) b = true;}
            if(y1.equals(y2)){ b = x.equals(x1);}
        }
        return b;
    }

    private boolean membership_point2(Double x,Double y){
        boolean b = false;
        boolean b1 = line2.membership_point(x,y);
        if(b1){
            if(y1>=y2){if(y >= y2 & y <= y1) b = true;}
            if(y2>=y1){if(y >= y1 & y <= y2) b = true;}
            if(y1.equals(y2)){
                    b = x == x1;}
        }
        return b;
    }

    private boolean membership_point3(Double x,Double y){
        boolean b = false;
        boolean b1 = line3.membership_point(x,y);
        if(b1){
                if(x1>=x2)  b = x >= x2 & x <= x1;
                if(x2>=x1)  b = x >= x1 & x <= x2;
        }
        return b;
    }

    private boolean membership_point4(Double x,Double y){
        boolean b = false;
        boolean b1 = line4.membership_point(x,y);
        if(b1){
                if(x1>=x2)  b = x >= x2 & x <= x1;
                if(x2>=x1)  b = x >= x1 & x <= x2;
        }
        return b;
    }

    private boolean membership_points(Double x,Double y){
        if (membership_point1(x,y) | membership_point2(x,y) | membership_point3(x,y) | membership_point4(x,y)) return true;
        else return false;
    }

    public Object[] intrsc_Line(Line line){
        Object intersect[] = new Object[9];    intersect[0] = false;   for (int i = 1; i < 9; i++) {intersect[i] = -0.0;}
        Line thisline = new Line();
        int num =1;
        for(int i = 0; i < 4; i++) {
            if(i==0){ thisline = line1;}
            if(i==1){thisline = line2;}
            if(i==2){ thisline = line3;}
            if(i==3){thisline = line4;}
            Object intrsWline[] = thisline.intersection(line);
            boolean b = (boolean) intrsWline[0] & this.membership_points((Double) intrsWline[1], (Double) intrsWline[2]);
            if (b) {
                intersect[0] = true;
                intersect[num] = intrsWline[1]; num++;
                intersect[num] = intrsWline[2]; num++;}
            if(!b){num += 2;}
        }
        return intersect;
    }

    public Object[][][] intrsc_Rect_Triangle(Triangle t){
        Object[][][]intrR_T = new Object[4][3][3];
        Line thisline = new Line();

        for(int i = 0; i < 4; i++){for(int k = 0; k < 3; k++){for(int j = 0; j < 3; j++)
        {intrR_T[i][k][j] = -0.0; if(j==0)intrR_T[i][k][j] = false;}}}

        for(int i = 0; i < 4; i++) {
            if(i==0){ thisline = line1;}
            if(i==1){ thisline = line2;}
            if(i==2){ thisline = line3;}
            if(i==3){ thisline = line4;}
        Object[][]intrT_L = t.trianle_intersection_with_line(thisline);

            for(int k = 0; k < 3; k++) {
            boolean b = (boolean)intrT_L[k][0];
            Double x = (Double)intrT_L[k][1];
            Double y = (Double)intrT_L[k][2];
        if( b & membership_points(x,y)){intrR_T[i][k][0] = intrT_L[k][0]; intrR_T[i][k][1] = intrT_L[k][1]; intrR_T[i][k][2] = intrT_L[k][2];}
        }}
        return intrR_T;
    }

    public Object[][] intrsc_Rect_Rect(Rectangle r){
        Object[][]intrR_R = new Object[4][9];
        Line thisline = new Line();

        for(int i = 0; i < 4; i++){for(int k = 0; k < 9; k++){
        intrR_R[i][k] = -0.0; if(k==0)intrR_R[i][k] = false;}}

            for(int i = 0; i < 4; i++) {
                if(i==0){ thisline = line1;}
                if(i==1){ thisline = line2;}
                if(i==2){ thisline = line3;}
                if(i==3){ thisline = line4;}

                Object[]intrR_L = r.intrsc_Line(thisline);
                if((boolean)intrR_L[0]){    intrR_R[i][0] = intrR_L[0];
                    for (int j = 1; j < 9; j++) {

                if(membership_points((Double)intrR_L[j],(Double)intrR_L[j+1]  )){

                        intrR_R[i][j] = intrR_L[j];
                        intrR_R[i][j+1] = intrR_L[j+1];

                } j++;
                    }}
    }
return intrR_R;
    }

    public Object[][] intrsc_Rect_Circle(Circle c){
        Object[][]r_C = new Object[4][5];  for (int i = 0; i < 4; i++){for (int k = 0; k < 5; k++){   r_C[i][k] = -0.0; if(k==0){r_C[i][k] = false;} }}
            Line thisline = new Line();
        for(int i = 0; i < 4; i++) {
            if(i==0){ thisline = line1;}
            if(i==1){thisline = line2;}
            if(i==2){ thisline = line3;}
            if(i==3){thisline = line4;}
            Object c_L[] = c.intersection_with_line(thisline);
            boolean b = (boolean)c_L[0];
            boolean b1 = membership_points((Double)c_L[1],(Double)c_L[2]);
            boolean b2 = membership_points((Double)c_L[3],(Double)c_L[4]);
              if(b){
                  if(b1){r_C[i][0] = true; r_C[i][1] = c_L[1]; r_C[i][2] = c_L[2];}
                  if(b2){r_C[i][0] = true; r_C[i][3] = c_L[3]; r_C[i][4] = c_L[4];}
              }
        }
        return r_C;
    }





}
