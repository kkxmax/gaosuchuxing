package com.chengxin.bfip.manage;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;

import com.chengxin.bfip.CommonUtil;
import com.chengxin.bfip.Constants;
import com.chengxin.bfip.model.Xyleixings;
import com.chengxin.bfip.model.XyleixingsDAO;
import com.chengxin.common.BaseController;
import com.chengxin.common.JavascriptUtil;
import com.chengxin.common.KeyValueString;

/**
 * 
 * @author Administrator
 */
public class XyleixingController extends BaseController {

	private XyleixingsDAO xyleixingDao = null;

	public void setxyleixingDao(XyleixingsDAO value) {
		this.xyleixingDao = value;
	}

	public XyleixingController() throws Exception {

	}

	public ModelAndView init(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {
		if(!SessionUtil.isLogined(request)) {
            return JavascriptUtil.MessageMove(request, response, "", "login.html");
        }
		if(!this.checkManagePermission(request, "xyleixing.html")) {
			return JavascriptUtil.MessageMove(request, response, "您没有权限", "");
		}
    	request.setAttribute("user_info", SessionUtil.getSessionVar(request, "USER_INFO"));
		request.setAttribute("cur_page", "xyleixing.html");
		request.setAttribute("title", new String[] { "行业类型"});
		request.setAttribute("breadcrumbs", new KeyValueString[] { new KeyValueString("行业类型", "xyleixing.html") });

		String action = this.getBlankParameter(request, "pAct", "");

		if (action.equals("") || action.equals("index")) {
			return this.index(request, response, session);
		} else if (action.equals("treeList")) {
			return this.treeList(request, response, session);
		} else if (action.equals("remove")) {
			return this.remove(request, response, session);
		} else if (action.equals("edit")) {
			return this.edit(request, response, session);
		} else if (action.equals("editDo")) {
			return this.editDo(request, response, session);
		} else if (action.equals("validate")) {
			return this.validate(request, response, session);
		} 

		return null;
	}

	public ModelAndView index(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		return new ModelAndView("manage/xyleixing/index");
	}
	
	public ModelAndView edit(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		String id = this.getBlankParameter(request, "id", "");
		
		String where = "upper_id = 0";
		if(!id.isEmpty()) {
			where += " and id !=" + id;
		}
		List<Xyleixings> rootLeixings = xyleixingDao.search(null, where, "write_time desc");
		Xyleixings record = null;
		if(!id.isEmpty()) {
			record = xyleixingDao.getDetail(Integer.valueOf(id));
		}
		
		request.setAttribute("root_leixings", rootLeixings);
		request.setAttribute("record", record);
		
		return new ModelAndView("manage/xyleixing/edit");
	}

	public ModelAndView editDo(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		JSONObject result = new JSONObject();

		String id = this.getBlankParameter(request, "id", "");
		String title = this.getBlankParameter(request, "title", "");
		String upper_id = this.getBlankParameter(request, "upper_id", "");

		Xyleixings object;
		if(id.isEmpty()) {
			object = new Xyleixings();	
		}
		else {
			object = xyleixingDao.get(Integer.valueOf(id));
		}
		
		object.setTitle(title);
		object.setUpper_id(CommonUtil.toIntDefault(upper_id));

		if(id.isEmpty()) {
			xyleixingDao.insert(object);
			result.put("retcode", 200);
			result.put("msg", "新增成功");
		}
		else {
			xyleixingDao.update(object);
			result.put("retcode", 200);
			result.put("msg", "更新成功");
		}

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");
	}

	public ModelAndView treeList(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		JSONArray result = new JSONArray();
		
		List<Xyleixings> itemList = xyleixingDao.search(null, "", "write_time desc");

		for (int i = 0; i < itemList.size(); i++) {
			Xyleixings row = itemList.get(i);

			TreeObject dataItem = new TreeObject();
			
			dataItem.setId("cat_" + row.getId());
			dataItem.setType("default");
			dataItem.setText(row.getTitle());
			if(row.getUpper_id() == 0) {
				dataItem.setParent("#");
			}
			else {
				dataItem.setParent("cat_" + row.getUpper_id());	
			}
			
			result.add(dataItem);
		}

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");
	}

	public ModelAndView remove(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		JSONObject result = new JSONObject();

		String id = this.getBlankParameter(request, "id", "");

		xyleixingDao.delete(Integer.valueOf(id));

		result.put("retcode", 200);
		result.put("msg", "删除成功");

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");
	}
	
	public ModelAndView validate(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		boolean result = true;
		
		String rule = this.getBlankParameter(request, "rule", "");
		String id = this.getBlankParameter(request, "id", "");
		String title = this.getBlankParameter(request, "title", "");

		if(rule.equals("unique")) {
			String where = "title='" + title + "'";
			if(!id.isEmpty()) {
				where += " AND id !=" + id;
			}
			int cnt = xyleixingDao.count(null, where);
			
			if(cnt > 0) {
				result = false;
			}
		}

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");
	}
	
	public class TreeObject extends Object {
		private String id;
		private String type;
		private String text;
		private String parent;
		
		public String getId() {
			return id;
		}
		public void setId(String id) {
			this.id = id;
		}
		public String getType() {
			return type;
		}
		public void setType(String type) {
			this.type = type;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public String getParent() {
			return parent;
		}
		public void setParent(String parent) {
			this.parent = parent;
		}
		
	}
}