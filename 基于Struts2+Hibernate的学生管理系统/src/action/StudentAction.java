package action;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;
import dao.Loginregister;
public class StudentAction extends ActionSupport {
private HttpServletRequest request;
	@Override
	public String execute() throws Exception {
		// TODO Auto-generated method stub
		request=ServletActionContext.getRequest();
		Loginregister ge=new Loginregister();
		List list=ge.Allcy();
		if(list!=null)
		{
		request.getSession().setAttribute("list", list);
		return SUCCESS;
		}
		else
		{
			return ERROR;
		}
		
	}

}
