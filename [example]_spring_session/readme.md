Thực hiện phân quyền sử dụng phương thức với annotation @EnableGlobalMethodSecurity(prePostEnabled = true), 
đọc thông tin chi tiết <a href="http://websystique.com/spring-security/spring-security-4-method-security-using-preauthorize-postauthorize-secured-el/">tại đây</a><br>
remember-me với _key("uniqueAndSecret")_ concurrent session control với sessionManagement(), xem lại ví dụ [example]_remember_me_And_one_login > lưu trữ token trong database.<br>
<ul>
    <li>Set timeout session với thuộc tính server.servlet.session.timeout=15m</li>
    <li>Tài liệu khi làm việc với sessionManagement() đọc <a href="https://www.baeldung.com/spring-security-session">tại đây</a>, Xem kỹ các mục 2-5-7-9</li>
</ul>