<h3>Custom</h3>
<ol>
    <li>Interface AccessDeniedHandler > Xử lý khi truy cập tài nguyên bị cấm.</li>
    <li>Interface SuccessHandler > Xử lý khi truy cập thành công (thường là redirect).</li>
</ol>
<h3>Notice</h3>
<ul>
    <li>Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities(); > lấy ra danh sách các quyền của user hiện tại. > là sự thể hiện của GrantedAuthority</li>
    <li>grantedAuthority.getAuthority() > trả về quyền với tiền tố + ROLE_'...'</li>
    <li>sử dụng phương thức isCommited() của response để kiểm tra server đã cam kết và mở luồng để trả về dữ liệu cho brower chưa?</li>
</ul>

<h3>Interface RedirectStrategy</h3>
A strategy for determining if an HTTP request should be redirected to a new location in response to an HTTP response received from the target server.

