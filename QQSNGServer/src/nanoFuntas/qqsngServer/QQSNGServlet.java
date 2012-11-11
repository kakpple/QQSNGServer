package nanoFuntas.qqsngServer;

import java.io.IOException;
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

		PrintWriter out = null;
		try {
			out = response.getWriter();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
		
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
	}

}
