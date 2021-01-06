public class Test{
    public static void main (String[] args){
        String s = "sinem";
        System.out.println("s1="+s);
        StringBuffer sb = new StringBuffer(s);
        sb.delete(0, s.length()); 
        System.out.println("s2="+sb);
    }

}