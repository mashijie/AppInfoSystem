package cn.appsys.controller;

import java.io.File;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

import cn.appsys.pojo.AppCategory;
import cn.appsys.pojo.AppInfo;
import cn.appsys.pojo.AppVersion;
import cn.appsys.pojo.DataDictionary;
import cn.appsys.pojo.DevUser;
import cn.appsys.service.AppCategoryService;
import cn.appsys.service.AppInfoService;
import cn.appsys.service.DataDictionaryService;
import cn.appsys.service.impl.AppCategoryServiceImpl;
import cn.appsys.service.impl.AppInfoServiceImpl;
import cn.appsys.service.impl.AppVersionServiceImpl;
import cn.appsys.service.impl.DataDictionaryServiceImpl;
import cn.appsys.tools.Constants;
import cn.appsys.tools.PageSupport;

@Controller
@RequestMapping("dev/flatform/app")
public class AppController {

	@Resource
	DataDictionaryService dataDictionaryService;
	@Resource
	AppInfoService appInfoService;
	@Resource
	AppCategoryService appCategoryService;
	@Resource
	AppVersionServiceImpl appVersionServiceImpl;

	/**
	 * 查询列表
	 */
	@RequestMapping("list")
	public String appList(
			HttpServletRequest request,
			@RequestParam(value = "querySoftwareName", required = false) String querySoftwareName,
			@RequestParam(value = "queryStatus", required = false) String _queryStatus,
			@RequestParam(value = "queryFlatformId", required = false) String _queryFlatformId,
			@RequestParam(value = "queryCategoryLevel1", required = false) String _queryCategoryLevel1,
			@RequestParam(value = "queryCategoryLevel2", required = false) String _queryCategoryLevel2,
			@RequestParam(value = "queryCategoryLevel3", required = false) String _queryCategoryLevel3,
			@RequestParam(value = "pageIndex", required = false) String pageIndex) {
		if (_queryStatus == null || "".equals(_queryStatus)) {
			_queryStatus = "0";
		}
		if (_queryFlatformId == null || "".equals(_queryFlatformId)) {
			_queryFlatformId = "0";
		}
		if (_queryCategoryLevel1 == null || "".equals(_queryCategoryLevel1)) {
			_queryCategoryLevel1 = "0";
		}
		if (_queryCategoryLevel2 == null || "".equals(_queryCategoryLevel2)) {
			_queryCategoryLevel2 = "0";
		}
		if (_queryCategoryLevel3 == null || "".equals(_queryCategoryLevel3)) {
			_queryCategoryLevel3 = "0";
		}
		List<DataDictionary> statusList = dataDictionaryService
				.getdataDictionary("APP_STATUS");
		List<DataDictionary> flatFormList = dataDictionaryService
				.getdataDictionary("APP_FLATFORM");
		Integer index = pageIndex == null ? 0
				: (Integer.parseInt(pageIndex) - 1) * Constants.pageSize;
		List<AppInfo> appinfoList = appInfoService.getAppInFolist(
				querySoftwareName, Integer.parseInt(_queryStatus),
				Integer.parseInt(_queryFlatformId),
				Integer.parseInt(_queryCategoryLevel1),
				Integer.parseInt(_queryCategoryLevel2),
				Integer.parseInt(_queryCategoryLevel3), index);
		// 获取数据总数
		Integer appinfoListCount = appInfoService.getAppInFolistCount(
				querySoftwareName, Integer.parseInt(_queryStatus),
				Integer.parseInt(_queryFlatformId),
				Integer.parseInt(_queryCategoryLevel1),
				Integer.parseInt(_queryCategoryLevel2),
				Integer.parseInt(_queryCategoryLevel3)).size();
		PageSupport pages = new PageSupport();
		pages.setPageSize(Constants.pageSize);
		pages.setTotalCount(appinfoListCount);
		pages.setCurrentPageNo(pageIndex == null ? 1 : Integer
				.parseInt(pageIndex));

		// 存入页码数信息
		request.setAttribute("pages", pages);
		// 初次必须加载一级分类
		List<AppCategory> categoryLevel1List = appCategoryService
				.getAppCategoryList(null);
		// 存入1级目录
		request.getSession().setAttribute("categoryLevel1List",
				categoryLevel1List);
		// 存入和商品列表
		request.getSession().setAttribute("appInfoList", appinfoList);
		// 存入状态列表
		request.getSession().setAttribute("statusList", statusList);
		// 存入平台列表
		request.getSession().setAttribute("flatFormList", flatFormList);
		// 存入查询软件名称信息
		request.getSession().setAttribute("querySoftwareName",
				querySoftwareName);
		// 存入查询状态信息
		request.getSession().setAttribute("queryStatus", _queryStatus);
		// 存入查询订单信息
		request.getSession().setAttribute("queryFlatformId", _queryFlatformId);
		// 将一级分类信息设置为初始状态，后续使用ajax技术支持
		request.getSession().setAttribute("queryCategoryLevel1", "0");

		for (AppInfo appinfo : appinfoList) {
			System.out.println(appinfo.getSoftwareName());
		}

		return "developer/appinfolist";

	}

	/**
	 * 进入APP信息添加页面
	 */
	@RequestMapping("appinfoadd")
	public String appinfoadd() {
		return "developer/appinfoadd";
	}

	/**
	 * 添加页面加载商品所属平台信息
	 */
	@RequestMapping("datadictionarylist.json")
	@ResponseBody
	public Object datadictionarylist(
			@RequestParam(value = "tcode", required = false) String tcode) {
		List<DataDictionary> dataDictionaries = dataDictionaryService
				.getdataDictionary(tcode);
		return dataDictionaries;
	}

	/**
	 * 对添加名称进行异步验证，防止名字重复
	 */
	@RequestMapping("apkexist.json")
	@ResponseBody
	public Object apkexist(
			@RequestParam(value = "APKName", required = false) String APKName) {
		HashMap<String, String> result = new HashMap<String, String>();
		if (APKName == null || "".equals(APKName)) {
			result.put("APKName", "empty");
		} else {
			for (AppInfo appInfo : appInfoService.getAppInFolist(null, null,
					null, null, null, null, null)) {
				if (APKName.equals(appInfo.getAPKName())) {
					result.put("APKName", "exist");
					break;
				} else {
					result.put("APKName", "noexist");
				}
			}
		}
		return result;
	}

	/**
	 * App添加提交sava方法
	 */
	@RequestMapping(value = "appinfoaddsave")
	public String appInfoAddSave(AppInfo appinfo,
			@RequestParam("a_logoPicPath") MultipartFile attach,
			HttpServletRequest request) {
		// 为新建App添加图片路径
		String logoPicPath = null;
		String logoLocPath = null;
		// 定义服务器路径名
		String path = request.getSession().getServletContext()
				.getRealPath("statics" + File.separator + "uploadfiles");
		// 定义源文件名
		String oldPathName = attach.getOriginalFilename();
		// 获取源文件后缀名
		String prefix = FilenameUtils.getExtension(oldPathName);
		// 定义文件大小 1024=1kb
		int fileSize = 500000;
		// 判断文件大小是否超出预期
		if (attach.getSize() > fileSize) {
			request.setAttribute("fileUploadError", "上传文件大小不得超过500KB");
			return "developer/appinfoadd";
		} else if (prefix.equalsIgnoreCase("jpg") // 验证后缀名是否符合预期
				|| prefix.equalsIgnoreCase("jpeg")
				|| prefix.equalsIgnoreCase("png")
				|| prefix.equalsIgnoreCase("pneg")) {
			// 定义文件名
			String fileName = appinfo.getAPKName() + ".jpg";
			// 定义文件 path为路径
			File targetFile = new File(path, fileName);
			// 判断文件是否存在，如果不存在，执行mkdirs方法创建文件
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				// 将图片输出到目标文件夹内
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("fileUploadError", "文件上传失败");
				return "developer/appinfoadd";
			}
			// 获取文件路径名
			logoPicPath = request.getContextPath() + "/statics/uploadfiles/"
					+ oldPathName;
			// 获取服务器存储文件路径名
			logoLocPath = path + File.separator + oldPathName;
		} else {
			request.setAttribute("fileUploadError",
					"上传文件格式必须为jpg,jpeg,png,pneg格式");
			return "developer/appinfoadd";
		}

		// 为新建App添加作者和创建时间
		DevUser devUser = (DevUser) request.getSession().getAttribute(
				Constants.DEV_USER_SESSION);
		appinfo.setDevId(devUser.getId());
		appinfo.setCreatedBy(devUser.getId());
		appinfo.setCreationDate(new Date());
		appinfo.setLogoLocPath(logoLocPath);
		appinfo.setLogoPicPath(logoPicPath);
		if (appInfoService.addAppInfo(appinfo) != 0) {
			return "redirect:/dev/flatform/app/list";
		}
		request.setAttribute("fileUploadError", "上传失败");
		return "developer/appinfoadd";
	}

	/**
	 * ajax 返回分类类别
	 */
	@RequestMapping(value = "categorylevellist.json", method = RequestMethod.GET)
	@ResponseBody
	public Object appList(
			@RequestParam(value = "pid", required = false) String pid) {
		pid = pid == null || "".equals(pid) ? "0" : pid;
		List<AppCategory> appList = appCategoryService
				.getAppCategoryList(Integer.parseInt(pid));
		return appList;
	}

	/**
	 * 跳转版本添加页面
	 * 
	 * @return
	 */
	@RequestMapping(value = "appversionadd", method = RequestMethod.GET)
	public String appversionadd(@RequestParam("id") String id,
			HttpServletRequest request) {
		List<AppVersion> appVersions = appVersionServiceImpl
				.getAppVersionList(Integer.parseInt(id));
		AppVersion appVerSion = new AppVersion();
		appVerSion.setAppId(Integer.parseInt(id));
		request.setAttribute("appVersionList", appVersions);
		request.setAttribute("appVersion", appVerSion);
		return "developer/appversionadd";
	}

	/**
	 * 
	 * 保存版本信息
	 * 
	 * @return
	 */
	@RequestMapping(value = "addversionsave", method = RequestMethod.POST)
	public String addversionsave(AppVersion appVersion,
			@RequestParam("a_downloadLink") MultipartFile attach,
			HttpServletRequest request) {
		String apkFileName = "";
		String apkLocPath = "";
		String downLoadLink = "/AppInfoSystem/statics/uploadfiles/";
		String path = request.getSession().getServletContext()
				.getRealPath("statics" + File.separator + "uploadfiles");
		String oldPathName = attach.getOriginalFilename();
		String prefix = FilenameUtils.getExtension(oldPathName);
		int fileSize = 500000;
		if (attach.getSize() > fileSize) {
			request.setAttribute("fileUploadError",
					Constants.FILEUPLOAD_ERROR_4);
			return "developer/appversionadd";
		} else if (prefix.equals("apk")) {
			apkLocPath = path + File.separator + oldPathName;
			apkFileName = oldPathName;
			downLoadLink += oldPathName;
			appVersion.setApkFileName(apkFileName);
			appVersion.setApkLocPath(apkLocPath);
			appVersion.setDownloadLink(downLoadLink);
			// 定义文件保存名
			String fileName = appVersion.getVersionNo() + "-" + oldPathName;
			// 定义文件 path为路径
			File targetFile = new File(path, fileName);
			// 判断文件是否存在，如果不存在，执行mkdirs方法创建文件
			if (!targetFile.exists()) {
				targetFile.mkdirs();
			}
			try {
				// 将图片输出到目标文件夹内
				attach.transferTo(targetFile);
			} catch (Exception e) {
				e.printStackTrace();
				request.setAttribute("fileUploadError",Constants.FILEUPLOAD_ERROR_2);
				return "developer/appversionadd";
			}
			//成功后！处理操作
			DevUser devUser = (DevUser) request.getSession().getAttribute(
					Constants.DEV_USER_SESSION);
			appVersion.setCreatedBy(devUser.getId());
			appVersion.setCreationDate(new Date());
			appVersionServiceImpl.addVersion(appVersion);
			return "redirect:/dev/flatform/app/list";
		} else {
			request.setAttribute("fileUploadError",
					Constants.FILEUPLOAD_ERROR_3);
			return "developer/appversionadd";
		}
	}

	@RequestMapping(value = "{appId}/sale.json")
	@ResponseBody
	public Object sale(@PathVariable String appid, HttpSession session) {
		HashMap<String, Object> result = new HashMap<String, Object>();
		Integer appId = 0;
		try {
			appId = Integer.parseInt(appid);
		} catch (Exception e) {
			appId = 0;
		}
		result.put("errorCode", "0");
		result.put("appId", appid);
		if (appId > 0) {

		} else {
			result.put("errorCode", "param000001");
		}

		return result;
	}

}
