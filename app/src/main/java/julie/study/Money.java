package julie.study;


 public class Money {
     private int value;
     private String type;

     public Money(int v, String t){
     value = v;
     type = t;
     }

     public Money add(Money m){
     return new Money(value + m.getValue(), type);
     }

     public Money div(Money m){
     return new Money(value / m.getValue(), type);
     }

     public int getValue(){
     return value;
     }
 }
