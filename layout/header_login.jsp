

<%@page import="service.UserService"%>
<%@page import="dto.UserDTO"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
    <%
	    String id = (String) session.getAttribute("id");
    	UserService userService = new UserService();
    	UserDTO user = userService.userInfo(id);
    	String name = (user != null) ? user.getName() : "DefaultName";
    %>
    
		<div class="top-container">
			<header class="header_login">
				<div>
					<a href="/library" class="li_name">HOME</a>
				</div>
				<ul class="right_ul">
					<li><a href="/library/intro/logout.jsp" onclick="return confirm('로그아웃하시겠습니까?')">로그아웃</a>
					<li><a href="/library/myPage">마이페이지</a>
				</ul>
			</header>

			<div class="home_search">
				<a href="/library"><img alt="홈 버튼" src="/library/images/wooseok.png"></a>
				<!-- 검색 기능 아직 없음 -->
				<form class="search" action="/library/bookList?menu=search"  method="post">
					<input name = "searchBook" type="text" placeholder="검색어를 입력하세요.">
					<button type="submit"><img src="/library/images/search.png"></button>
				</form>
			</div>
		</div>
	 	<!-- end of top-container -->