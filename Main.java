package com.student.demo;

import java.sql.*;
import java.util.Scanner;

public class Main {
    static final String url = "jdbc:mysql://localhost:3306/studentDb";
    static final String admin = "root";
    static final String password ="abhi1203";
    static Connection cd;
    public static void main(String[] args) {
        try{
            cd = DriverManager.getConnection(url,admin,password);
            Scanner sc = new Scanner(System.in);
            while (true){
                System.out.println("\n Add student:1");
                System.out.println("\n Delete student:2");
                System.out.println("\n Update student:3");
                System.out.println("\n view student:4");
                System.out.println("\n Exit:5");
                int choice;
                System.out.println("Enter the choice");
                choice = sc.nextInt();
                sc.nextLine();
                switch (choice){
                    case 1:
                        addStudent(sc);
                        break;
                    case 2:
                        deleteStudent(sc);
                        break;
                    case 3:
                        updateStudent(sc);
                        break;
                    case 4:
                        viewStudent();
                        break;
                    case 5:
                        System.out.println("Exiting........");
                        return;
                }

            }
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    static void addStudent(Scanner sc) throws SQLException{
        System.out.println("Enter the name");
        String name = sc.nextLine();
        System.out.println("Enter the course");
        String course = sc.nextLine();
        String sql = "INSERT INTO student (name,course) values (?,?)";
        PreparedStatement ps = cd.prepareStatement(sql);
        ps.setString(1,name);
        ps.setString(2,course);
        ps.executeUpdate();
        System.out.println("Student value is added");
    }
    static void deleteStudent(Scanner sc) throws SQLException{
        System.out.println("Enter the id");
        int id = sc.nextInt();
        String sql = "Delete from student where id=?";
        PreparedStatement ps = cd.prepareStatement(sql);
        ps.setInt(1,id);
        int rows = ps.executeUpdate();
        if(rows>0){
            System.out.println("Deleted Successfully");
        }
        else{
            System.out.println("User not found");
        }
    }
    static void updateStudent(Scanner sc) throws SQLException{
        System.out.println("Enter the id");
        int id = sc.nextInt();
        sc.nextLine();
        System.out.println("What need to update name or course");
        String changeContent = sc.nextLine().toLowerCase();

        String sql = "",sq1 = "";
        int rows=0;
        if(changeContent.equals("name")){
            System.out.println("Enter the name to change");
            String changeName = sc.nextLine();
            sql = "UPDATE student set name=? where id=?";
            PreparedStatement ps = cd.prepareStatement(sql);
            ps.setInt(2,id);
            ps.setString(1,changeName);
            rows = ps.executeUpdate();
        }
        else if(changeContent.equals("course")){
            sq1 = "UPDATE student set course=? where id =?";
            System.out.println("Enter the name to change");
            String changeCourse= sc.nextLine();
            PreparedStatement pd = cd.prepareStatement(sq1);
            pd.setInt(2,id);
            pd.setString(1,changeCourse);
            rows = pd.executeUpdate();
        }
        if(rows>0){
            System.out.println("User updated successfully");
        }
        else {
            System.out.println("User not found");
        }
    }
    public static void viewStudent() throws java.sql.SQLException{
        String sql = "SELECT * FROM student";
        Statement st_query = cd.createStatement();
        ResultSet set = st_query.executeQuery(sql);
        System.out.println("Id || Name || Course");
        while(set.next()){
            int id_get=set.getInt("id");
            String tableName=set.getString("name");
            String course_name= set.getString("course");
            System.out.println(id_get+" "+tableName+" "+course_name);
        }

    }


}
