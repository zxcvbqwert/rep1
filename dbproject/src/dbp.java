import java.sql.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

class DatabaseConnect
{
    public static Statement stmt;
    public static String query;
    public static Connection con;

    private String driver, db, url, user, pass;

    DatabaseConnect()
    {
        con = null;
        driver = "com.mysql.jdbc.Driver";
        db = "SocialAppData";
        url = "jdbc:mysql://localhost:3306/" + db;
        user = "root";
        pass = "mysql123";

        try
        {
            Class.forName(driver);
            con = DriverManager.getConnection(url, user, pass);
            System.out.println("\nConnection Established with database..."+db);
            stmt = con.createStatement();
        }
        catch(Exception e)
        {   System.out.println(e);
        }
    }
}

class LoginPage extends JFrame
{
    private JLabel l1, l2, l3, l4;
    private JTextField user;
    private JPasswordField pass;
    private JButton login, register;

    class ClickLogin implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                String username = user.getText();
                String password = pass.getText();

                DatabaseConnect.query = "select * from Users where UserName = '"+username+"' AND Password = '"+password+"';";
                ResultSet rs = DatabaseConnect.stmt.executeQuery(DatabaseConnect.query);

                if(rs.next())
                {
                    if(username.equals(rs.getString(1))&&password.equals(rs.getString(5)))
                    {
                        // redirect to FeedPage();
                        l3.setText("Login Successfull!");
                        dispose();
                    }
                }
                else
                    l3.setText("Incorrect Username or Password!");
            }
            catch (Exception f)
            {   System.out.println(f);
            }
        }
    }

    class ClickRegisterNow implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new RegisterPage();
            dispose();
        }
    }

    LoginPage()
    {
        setTitle("Login");
        setLayout(null);
        setSize(400, 400);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Username: "); l1.setBounds(25, 25, 100, 25); add(l1);
        user = new JTextField(); user.setBounds(150, 25, 150, 25); add(user);

        l2 = new JLabel("Password: "); l2.setBounds(25, 75, 100, 25); add(l2);
        pass = new JPasswordField(); pass.setBounds(150, 75, 150, 25); add(pass);

        login = new JButton("Login"); login.setBounds(125, 125, 100, 25); add(login); login.setActionCommand("login"); login.addActionListener(new ClickLogin());

        l3 = new JLabel(); l3.setBounds(75, 175, 300, 25); add(l3);

        l4 = new JLabel("Dont have an account?"); l4.setBounds(25, 225, 250, 25); add(l4);
        register = new JButton("Register Now"); register.setBounds(200, 225, 150, 25); add(register); register.setActionCommand("register"); register.addActionListener(new ClickRegisterNow());


    }
}

class RegisterPage extends JFrame
{
    private JLabel l1, l2, l3, l4, l5, l6, l7, l8;
    private JTextField user, fname, lname, email;
    private JPasswordField pass, cpass;
    private JButton register, login;

    class ClickRegister implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            try
            {
                String username = user.getText();
                String firstName = fname.getText();
                String lastName = lname.getText();
                String emailId = email.getText();
                String password = pass.getText();
                String cpassword = cpass.getText();

                // use if to apply regex on fields
                
                DatabaseConnect.query = "select * from users where Username = '"+username+"';";
                ResultSet rs = DatabaseConnect.stmt.executeQuery(DatabaseConnect.query);
                if(rs.next())
                    l7.setText("Username already taken!");

                if(!password.equals(cpassword))
                    l7.setText("Passwords Dont Match!");

                else
                {
                    DatabaseConnect.query = "insert into Users values('"+username+"','"+firstName+"', '"+lastName+"', '"+emailId+"', '"+password+"');";
                    System.out.println("No. of rows added in db: "+DatabaseConnect.stmt.executeUpdate(DatabaseConnect.query));
                    l7.setText("Registration Successfull!");
                    new LoginPage();
                    dispose();
                }
            }
            catch (Exception g)
            {   System.out.println(g);
            }
        }
    }

    class ClickLogin implements ActionListener
    {
        public void actionPerformed(ActionEvent e)
        {
            new LoginPage();
            dispose();
        }
    }

    RegisterPage()
    {
        setTitle("Register");
        setLayout(null);
        setSize(400, 500);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        l1 = new JLabel("Username: "); l1.setBounds(25, 25, 100, 25); add(l1);
        user = new JTextField(); user.setBounds(150, 25, 150, 25); add(user);

        l2 = new JLabel("FirstName: "); l2.setBounds(25, 75, 100, 25); add(l2);
        fname = new JTextField(); fname.setBounds(150, 75, 150, 25); add(fname);

        l3 = new JLabel("LastName: "); l3.setBounds(25, 125, 100, 25); add(l3);
        lname = new JTextField(); lname.setBounds(150, 125, 150, 25); add(lname);

        l4 = new JLabel("Email: "); l4.setBounds(25, 175, 100, 25); add(l4);
        email = new JTextField(); email.setBounds(150, 175, 150, 25); add(email);

        l5 = new JLabel("Password: "); l5.setBounds(25, 225, 100, 25); add(l5);
        pass = new JPasswordField(); pass.setBounds(150, 225, 150, 25); add(pass);

        l6 = new JLabel("Confirm Password: "); l6.setBounds(25, 275, 175, 25); add(l6);
        cpass = new JPasswordField(); cpass.setBounds(150, 275, 150, 25); add(cpass);

        register = new JButton("Register"); register.setBounds(125, 325, 100, 25); add(register); register.setActionCommand("register"); register.addActionListener(new ClickRegister());

        l7 = new JLabel(); l7.setBounds(100, 375, 300, 25); add(l7);

        l8 = new JLabel("Already have an account?"); l8.setBounds(25, 425, 250, 25); add(l8);

        login = new JButton("Login"); login.setBounds(200, 425, 100, 25); add(login); login.setActionCommand("login"); login.addActionListener(new ClickLogin());



    }
}



class dbp
{
    public static void main(String[] args) throws Exception
    {
        new DatabaseConnect();
        new LoginPage();


    }
}