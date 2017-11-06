package com.chengxin.bfip.manage;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.util.HtmlUtils;

import com.chengxin.bfip.Constants;
import com.chengxin.bfip.model.Errors;
import com.chengxin.bfip.model.ErrorsDAO;
import com.chengxin.common.BaseController;
import com.chengxin.common.DateTimeUtil;
import com.chengxin.common.JavascriptUtil;
import com.chengxin.common.KeyValueString;

/**
 * 
 * @author Administrator
 */
public class ErrorController extends BaseController {

	private ErrorsDAO memberDao = null;

	public ErrorsDAO getMemberDao() {
		return memberDao;
	}

	public void setMemberDao(ErrorsDAO value) {
		this.memberDao = value;
	}

	public ErrorController() throws Exception {

	}

	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		if (!SessionUtil.isLogined(request)) {
			return JavascriptUtil.MessageMove(request, response, "",
					"login.html");
		}
		if(!this.checkManagePermission(request, "error.html")) {
			return JavascriptUtil.MessageMove(request, response, "您没有权限", "");
		}
		request.setAttribute("user_info",
				SessionUtil.getSessionVar(request, "USER_INFO"));
		request.setAttribute("cur_page", "error.html");
		request.setAttribute("title", new String[] { "纠错管理", "纠错管理" });
		request.setAttribute("breadcrumbs",
				new KeyValueString[] { new KeyValueString("纠错管理",
						"error.html") });

		String action = this.getBlankParameter(request, "pAct", "");

		if (action.equals("") || action.equals("index")) {
			return this.index(request, response, session);
		} else if (action.equals("search")) {
			return this.search(request, response, session);
		} else if (action.equals("viewDetail")) {
			return this.viewDetail(request, response, session);
		} else if (action.equals("delete")) {
			return this.delete_record(request, response, session);
		} else if (action.equals("pass")) {
			return this.pass(request, response, session);
		} 

		return null;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {

		request.setAttribute("C_ERROR_STATUS_NAME", Constants.C_ACCOUNT_TEST_STATUS);
		//		request.setAttribute("C_error_METHOD_NAME", Constants.C_error_METHOD_NAME);
		//		String where = " upper_id is null ";
		//		List<Xyleixings> leixingList = leixingDao.search(null , where);
		//		ArrayList<String[]> data = new ArrayList<String[]>();
		//		for (int i = 0; i < leixingList.size(); i++) {
		//			Xyleixings row = leixingList.get(i);
		//			String[] dataItem = new String[] {
		//					String.valueOf(row.getId()),
		//					row.getTitle() 
		//			};
		//			data.add(dataItem);
		//		}
		//		request.setAttribute("C_Errors_KIND_NAME",  data);
		return new ModelAndView("manage/error/index");
	}

	public ModelAndView search(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) throws Exception {
		JSONObject result = new JSONObject();
		JSONObject filterParamObject = new JSONObject();

		String filterParam = this.getBlankParameter(request,
				"filter[filter_param]", "");
		String start = this.getBlankParameter(request, "start", "");
		String length = this.getBlankParameter(request, "length", "");
		String orderColIndex = this.getBlankParameter(request,
				"order[0][column]", "");
		String orderColName = this.getBlankParameter(request, "columns["
				+ orderColIndex + "][name]", "");
		String orderDir = this.getBlankParameter(request, "order[0][dir]",
				"asc");

		if (!filterParam.isEmpty()) {
			filterParam = HtmlUtils.htmlUnescape(filterParam);
			filterParamObject = JSONObject.fromObject(filterParam);
		}
		filterParamObject.put("start", start);
		filterParamObject.put("length", length);
		filterParamObject.put("order_col", orderColName);
		filterParamObject.put("order_dir", orderDir);

		String extraWhere = "";
		List<Errors> ErrorsList = memberDao.search(filterParamObject,
				extraWhere);
		int count = memberDao.count(filterParamObject, extraWhere);

		ArrayList<String[]> data = new ArrayList<String[]>();

		for (int i = 0; i < ErrorsList.size(); i++) {
			Errors row = ErrorsList.get(i);

			String opHtml = "<a href='error.html?pAct=viewDetail&id="
					+ String.valueOf(row.getId())
					+ "' class='btn btn-xs purple' data-target='#global-modal' data-toggle='modal'><i class='fa fa-edit'></i> 查看</a>";
			String[] dataItem = new String[] {
					row.getBooknum(), 
					row.getName(),
					DateTimeUtil.dateFormat(row.getWriteTime()),
					row.getNoName(),
					row.getRealName(),
					row.getContent(),
					row.getReason(),
					row.getStatusName(),
					row.getUpdateTime(), 
					opHtml 
			};
			data.add(dataItem);
		}
		result.put("recordsTotal", count);
		result.put("recordsFiltered", count);
		result.put("data", data);

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");

	}

	public ModelAndView viewDetail(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		JSONObject result = new JSONObject();

		String id = this.getBlankParameter(request, "id", "");

		Errors record = memberDao.getDetail(Integer.valueOf(id));

		request.setAttribute("record", record);

		return new ModelAndView("manage/error/detail");
	}

	public ModelAndView delete_record(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		JSONObject result = new JSONObject();

		String id = this.getBlankParameter(request, "id", "");

		Errors record = memberDao.get(Integer.valueOf(id));

		memberDao.delete(record);

		result.put("retcode", 200);
		result.put("msg", "操作成功");

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");


	}
	
	public ModelAndView pass(HttpServletRequest request, HttpServletResponse response, HttpSession session) throws Exception {

		JSONObject result = new JSONObject();

		String id = this.getBlankParameter(request, "id", "");

		Errors record = memberDao.get(Integer.valueOf(id));
		record.setStatus(2);
		memberDao.update(record);

		result.put("retcode", 200);
		result.put("msg", "操作成功");

		request.setAttribute("JSON", result);

		return new ModelAndView("json_result");


	}

}