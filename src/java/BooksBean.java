/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.io.Serializable;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;

/**
 *
 * @author junya
 */
@ManagedBean
@RequestScoped

public class BooksBean implements Serializable{

    /**
     * Creates a new instance of BooksBean
     */
    
    public BooksBean() {
    }
    
    //DB接続し、その中身をListに保持する。
    public ArrayList<Books> selectfrombooks() throws ClassNotFoundException, SQLException{
      //oracleのライブラリを読み込む
      Class.forName("oracle.jdbc.driver.OracleDriver");
      //oracleのDBに接続する
      Connection conn = DriverManager.getConnection("jdbc:oracle:thin:@(接続先IP）:（ポート番号、デフォルトは1521）:（接続識別子）","ユーザ","パスワード");
      //oracleとのセッションを張る
      Statement stmt = conn.createStatement();
      //上記で接続したDBに対して、SQLを実行する。
      //booksというテーブルから全データを取得している。
      ResultSet rset = stmt.executeQuery("select * from books");
      //取得したデータを格納するリスト
      ArrayList<Books> booklist = new ArrayList<Books>();
      //取得したデータをリストに格納する。
      while(rset.next()){
          Books books = new Books();
          books.setId(rset.getInt("id"));
          books.setName(rset.getString("name"));
          books.setAuthor(rset.getString("author"));
          //System.out.println(rset.getInt(1) + " " + rset.getString(2) + " " + rset.getString(3))
          booklist.add(books);
      }
      //SQL送信処理を切断
      rset.close();
      //セッションを切断
      stmt.close();
      //DB接続を切断
      conn.close();
      return booklist;
    }
}
