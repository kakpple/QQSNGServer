package nanoFuntas.qqsngServer;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class QQSNGServlet
 */
@WebServlet("/QQSNGServlet")
public class QQSNGServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	private boolean DEBUG = true;
	private String TAG = "QQSNGServlet";
    /**
     * Default constructor. 
     */
    public QQSNGServlet() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();	
		out.write("1");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		if(DEBUG) System.out.println(TAG + ": doPost");
		
		String strInput = null;
		
		InputStream inStrm = null;
		ObjectInputStream objInStrm = null;
		OutputStream outStrm = null;
		ObjectOutputStream objOutStrm = null;
		
		// get date from client
		inStrm = request.getInputStream();
		objInStrm = new ObjectInputStream(inStrm);
		try {
			strInput = (String) objInStrm.readObject();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		if(strInput.equals("55")){
			// write date to be sent to client
			outStrm = response.getOutputStream();
			objOutStrm = new ObjectOutputStream(outStrm);
			objOutStrm.writeObject(new String("99"));
			objOutStrm.flush();
			objOutStrm.close();
		}
		//out.write(new String("99"));
		
		/*
		String type = request.getParameter("REQ_TYPE");	
		DatabaseService mDatabaseService = new DatabaseService();		
		
		if(type.equals("FETCH_SELF_INFO")){
			String selfID = request.getParameter("SELF_ID");
			if( mDatabaseService.isUserRegistered(selfID) ){
				if(DEBUG) System.out.println(TAG + ": isUserRegistered == true");
				out.print(1);
			} else {
				if(DEBUG) System.out.println(TAG + ": isUserRegistered == false");
				mDatabaseService.registerUser(selfID);
				out.print(5);
			};			
		}
		*/
	}
}
