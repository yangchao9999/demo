<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/includes/common-import.jsp"%>
<%@ taglib uri="/page" prefix="p"%>
<!DOCTYPE html>
<!--[if IE 8]> <html lang="en" class="ie8"> <![endif]-->
<!--[if IE 9]> <html lang="en" class="ie9"> <![endif]-->
<!--[if !IE]><!--> <html lang="en"> <!--<![endif]-->
<head>
	<meta charset="utf-8" />
	<title>${systemName}</title>
	<meta content="width=device-width, initial-scale=1.0" name="viewport" />
	<meta content="" name="description" />
	<meta content="" name="author" />
	<link rel="stylesheet" href="${resPath}js/jquery-ui/jquery-ui.css">
	<script src="${resPath}js/jquery-ui/jquery-1.12.4.js"></script>
	<script src="${resPath}js/jquery-ui/jquery-ui.js"></script>
	<script src="${resPath}js/uniteTable.js"></script>
	<%@ include file="/WEB-INF/includes/common-component-list.jsp"%>
	<script type="text/javascript">
		jQuery(document).ready(function() { 
			$( "#examname" ).autocomplete({
			  source: "${basePath}exam/findExamListAjax"
		    });
			
			uniteTable(document.getElementById("sample_1"),2);
		});
	</script>
</head>
<body class="page-header-fixed">
	<%@ include file="/WEB-INF/includes/header.jsp"%>
	<!-- BEGIN CONTAINER -->
	<div class="page-container row-fluid">
		<%@ include file="/WEB-INF/includes/leftMenu.jsp"%>
		<div class="page-content">
			<!-- BEGIN PAGE CONTAINER-->        
			<div class="container-fluid">
				<!-- BEGIN PAGE HEADER-->
				<div class="row-fluid">
					<div class="span12">
						<h3 class="page-title">
							我的考试
						</h3>
					</div>
				</div>
				<div class="row-fluid">
					<div class="span12">
						<div class="tabbable tabbable-custom boxless">
						<ul class="nav nav-tabs">
							<li class=""><a href="${basePath }exam/update/${searchParams.map['eid'] }?tab=1">基础信息</a></li>
							<li><a class="" href="${basePath }exam/update/${searchParams.map['eid'] }?tab=2">高级配置</a></li>
							<li class="active"><a href="#tab_3" data-toggle="tab">考生信息</a></li>
							<li><a class="" href="${basePath }examRank/${searchParams.map['eid'] }">考试数据</a></li>
						</ul>
						<div class="tab-content">
							<div class="tab-pane active" id="tab_3">
								<div class="portlet box light-grey">
									<div class="portlet-title"></div>
									<div class="portlet-body">
										<form id="searchForm" class="horizontal-form" action="${basePath }examStu/${searchParams.map['eid'] }" method="post">
											<div class="portlet-body form">
												<div class="row-fluid">
													<div class="span3">
														<div class="control-group">
															<label class="control-label" for="truename">考生姓名</label>
															<div class="controls">
																<input class="m-wrap span10" type="text" placeholder="考生姓名..." id="truename" name="map['truename']" value="${searchParams.map['truename'] }"/>													
															</div>
														</div>
													</div>
													<div class="span3">
														<div class="control-group">
															<label class="control-label" for="idcard">准考证号</label>
															<div class="controls">
																<input class="m-wrap span10" type="text" placeholder="准考证号..." id="idcard" name="map['idcard']" value="${searchParams.map['idcard'] }"/>													
															</div>
														</div>
													</div>
												</div>
												<div style="text-align:center;">
													<button type="submit" class="btn blue"><i class="icon-ok"></i> 查询</button>
													<button type="button" class="btn" onclick="resetForm('searchForm');">重置</button>
												</div>
											</div>
											<div class="alert ${MESSAGE_STATE } ${empty MESSAGE ?'hide':'' }">
												<button class="close" data-dismiss="alert"></button>
												<span>${MESSAGE }</span>
											</div>
											<div class="portlet box light-grey">
												<div class="clearfix">
													<div class="btn-group">
														<a id="sample_editable_1_new" class="btn green" href="${basePath }examStu/create/${searchParams.map['eid'] }">
															新增考生 <i class="icon-plus"></i>
														</a>
													</div>
													<div class="btn-group">
														<a id="sample_editable_2_new" class="btn green" href="${basePath }examStu/imp/${searchParams.map['eid'] }">
															批量导入考生 <i class="icon-file"></i>
														</a>
													</div>
													<div class="btn-group pull-right">
														<c:if test="${business.account >= '0'}">
															<button class="btn dropdown-toggle" data-toggle="dropdown">更多 <i class="icon-angle-down"></i>
															</button>
															<ul class="dropdown-menu pull-right">
																<li><a onclick="submitForm('${basePath }examStu/export','searchForm');" href="javascript:void();">导出至Excel</a></li>
															</ul>
														</c:if>
													</div>
													<div class="btn-group pull-right">
														<select id="pageItem" name="map['pageItem']" class="span16 m-wrap">
												       		<option value="10" ${searchParams.map['pageItem'] == 10 ? 'selected=selected':''}>每页10条</option>
													   		<option value="20" ${searchParams.map['pageItem'] == 20 ? 'selected=selected':''}>每页20条</option>
													   		<option value="30" ${searchParams.map['pageItem'] == 30 ? 'selected=selected':''}>每页30条</option>
													   		<option value="50" ${searchParams.map['pageItem'] == 50 ? 'selected=selected':''}>每页50条</option>
													   		<option value="100" ${searchParams.map['pageItem'] == 100 ? 'selected=selected':''}>每页100条</option>
													   		<option value="200" ${searchParams.map['pageItem'] == 200 ? 'selected=selected':''}>每页200条</option>
												   		</select>
												   	</div>
												</div>
											</form>
											<table class="table table-striped table-bordered table-hover" id="sample_1">
												<thead>
													<tr>
														<th style="text-align: center;" class="span2">序号</th> 
														<th style="text-align: center;" class="span4">准考证号</th> 
														<th style="text-align: center;" class="span2">姓名</th>
														<th style="text-align: center;" class="hidden-480">考场信息</th>
														<th class="span4">操作</th>
													</tr>
												</thead>
												<tbody>
													<c:forEach items="${list}" var="list" varStatus="status">
														<tr class="odd gradeX">
															<td style="text-align: center;">${status.index+1}</td> 
															<td>${list.idcard }</td> 
															<td>${list.truename }
																<c:if test="${list.testFlag == '1'}"><span class="badge badge-warning">测试</span></c:if>
															</td> 
															<td class="hidden-480">${list.examSitename } ${list.examRoomname }</td>
															<td>
																<a href="${basePath}examStu/update/${list.id}" class="btn mini purple"><i class="icon-edit"></i> 修改</a>
																<a href="javascript:void();" onclick="delData('${basePath}examStu/delete/${list.id}');" class="btn mini black"><i class="icon-trash"></i> 删除</a>
															</td>
														</tr>
													</c:forEach>
												</tbody>
											</table>
											<p:page url="examStu/${searchParams.map['eid'] }" cpage="${page.cpage }" perItem="${page.perItem }" totalItem="${page.totalItem }"/>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>

<div id="responsiveTimeDelay" class="modal hide fade" tabindex="-1" data-width="760">
	<div class="modal-header">
		<button type="button" class="close" data-dismiss="modal" aria-hidden="true"></button>
		<h3>延时</h3>
	</div>
	<div class="modal-body">
		<div class="row-fluid">
			<div class="controls">
				
			</div>
		</div>
	</div>
	<div class="modal-footer">
		<button type="button" data-dismiss="modal" class="btn">关闭</button>
	</div>
</div>

<script>
    function reexamine(uid,examId,cid){
    	if(confirm("确定要进行重考吗？")){
    		var url = "${basePath}examStu/reexamine/" + uid + "/" + examId + "/" + cid;
    		window.location = url;
    	}
    	
    }
</script>
</html>
