package database;

import java.sql.*;
import java.util.ArrayList;

public class Questions {
    private String question;
    private int id;
    private ArrayList<String> listQ=new ArrayList<String>();
    Connection conn = ConnectionBuilder.createConnection();
    Statement state = conn.createStatement();
    ResultSet res=null, res1=null;

    public Questions() throws SQLException {
    }

    public String getQuestion(){
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public ArrayList<String> bddselect(){
        String qDB="";
        try {
            String sql="SELECT * FROM question";
            res= state.executeQuery(sql);
            while (res.next()){
               question=res.getString("question");
               setQuestion(question);
               listQ.add(question);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

        return listQ;

    }

    public String picQuestion(String q){
        String pathDB="";
        int ans=0;
        try {
            String first="SELECT * FROM question WHERE question=?";
            PreparedStatement pstate= conn.prepareStatement(first);
            pstate.setString(1,q);
            res1=pstate.executeQuery();
            while (res1.next()){
                ans=res1.getInt("idQ");
            }
            System.out.println("id "+ans);
            String sql="SELECT * FROM picture WHERE idQ=?";
            PreparedStatement state= conn.prepareStatement(sql);
            state.setInt(1,ans);
            res= state.executeQuery();
            while (res.next()){
                System.out.println("je cherche");
                /*if(res.getString("imageLocation")==null){
                    pathDB="";
                }*/
                pathDB=res.getString("imageLocation");
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pathDB;
    }

    public ArrayList<String> getListQ() {
        return listQ;
    }

    public void setListQ(ArrayList<String> listQ) {
        this.listQ = listQ;
    }
}
