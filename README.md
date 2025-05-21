├── .gitattributes
├── .gitignore
├── .mvn
└── wrapper
│   └── maven-wrapper.properties
├── mvnw
├── mvnw.cmd
├── pom.xml
└── src
├── main
├── java
│   └── com
│   │   └── example
│   │       └── IS216_Dlegent
│   │           ├── Is216DlegentApplication.java
│   │           ├── config
│   │               ├── CloudinaryConfig.java
│   │               ├── MailConfiguration.java
│   │               ├── WebConfig.java
│   │               └── ZalopayConfig.java
│   │           ├── controller
│   │               ├── API
│   │               │   ├── AccountAPI.java
│   │               │   ├── AdminAPI.java
│   │               │   ├── CustomerController.java
│   │               │   ├── DanhGiaAPI.java
│   │               │   ├── DatPhongAPI.java
│   │               │   ├── DoiTacAPI.java
│   │               │   ├── GioHangAPI.java
│   │               │   ├── GoiDatPhongAPI.java
│   │               │   ├── KhuNghiDuongAPI.java
│   │               │   ├── LichSuDatPhongAPI.java
│   │               │   ├── LoaiPhongAPI.java
│   │               │   ├── MaGiamGiaAPI.java
│   │               │   ├── PasswordAPI.java
│   │               │   ├── PhongAPI.java
│   │               │   ├── ResortSearchAPI.java
│   │               │   ├── RoomDetailsAPI.java
│   │               │   ├── ServiceOfResortController.java
│   │               │   ├── ServicesAPI.java
│   │               │   ├── ThanhToanAPI.java
│   │               │   ├── UploadController.java
│   │               │   └── ZalopayAPI.java
│   │               ├── RoleGroupController.java
│   │               ├── SSR
│   │               │   ├── AdminViewController.java
│   │               │   ├── GetStartedViewController.java
│   │               │   ├── HelloController.java
│   │               │   ├── KhachHangViewController.java
│   │               │   ├── LichSuRutTienAdminDTO.java
│   │               │   ├── PartnerViewController.java
│   │               │   └── PaymentSuccessViewController.java
│   │               ├── SignInAPIController.java
│   │               └── SignInController.java
│   │           ├── encoder
│   │               └── Sha256PasswordEncoder.java
│   │           ├── middleware
│   │               ├── AdminInterceptor.java
│   │               ├── AuthInterceptor.java
│   │               ├── CustomerInterceptor.java
│   │               └── PartnerInterceptor.java
│   │           ├── model
│   │               ├── Account.java
│   │               ├── AccountAssignRole.java
│   │               ├── AccountRoleGroup.java
│   │               ├── AccountToken.java
│   │               ├── ChiTietDatPhong.java
│   │               ├── Customer.java
│   │               ├── DanhGia.java
│   │               ├── DatPhong.java
│   │               ├── DichVuMacDinh.java
│   │               ├── DichVuYeuCau.java
│   │               ├── DoiDiem.java
│   │               ├── DoiTac.java
│   │               ├── GiamGia.java
│   │               ├── GoiDatPhong.java
│   │               ├── HinhPhong.java
│   │               ├── HoaDon.java
│   │               ├── KhachHang.java
│   │               ├── KhoMaGiamGia.java
│   │               ├── KhuNghiDuong.java
│   │               ├── LichSuRutTien.java
│   │               ├── LoaiPhong.java
│   │               ├── Mail.java
│   │               ├── Phong.java
│   │               ├── Phuong.java
│   │               ├── Quan.java
│   │               ├── RoleGroup.java
│   │               ├── Services.java
│   │               ├── ServicesOfResort.java
│   │               ├── ThanhPho.java
│   │               ├── ThoiGianPhongBan.java
│   │               ├── TienIch.java
│   │               ├── User.java
│   │               ├── Utilities.java
│   │               ├── UtilitiesOfRoomType.java
│   │               └── elastic
│   │               │   └── ResortDocument.java
│   │           ├── payload
│   │               ├── SSR
│   │               │   ├── BienDongSoDuDTO.java
│   │               │   ├── KhuNghiDuongYeuThichDTO.java
│   │               │   └── RoomTypeDetailsDTO.java
│   │               ├── dto
│   │               │   ├── BookedRoomDTO.java
│   │               │   ├── BookingListDTO.java
│   │               │   ├── ChiTietDatPhongDTO.java
│   │               │   ├── ChiTietDatPhongDTO2.java
│   │               │   ├── DanhGiaDTO.java
│   │               │   ├── DichVuDTO.java
│   │               │   ├── DoiTacDTO.java
│   │               │   ├── GoiDatPhongDTO.java
│   │               │   ├── KhachHangDTO.java
│   │               │   ├── MaGiamGiaDTO.java
│   │               │   ├── PhongDTO.java
│   │               │   ├── ThoiGianPhongBanDTO.java
│   │               │   ├── ThoiGianYeuCauDTO.java
│   │               │   ├── ThongTinCaNhanKhachHangDTO.java
│   │               │   └── TienIchDTO.java
│   │               ├── request
│   │               │   ├── DeleteRoomTypeRequest.java
│   │               │   ├── InsertDanhGiaRequest.java
│   │               │   ├── InsertGioHang.java
│   │               │   ├── InsertGoiDatPhongRequest.java
│   │               │   ├── InsertResortRequest.java
│   │               │   ├── InsertRoomTypeRequest.java
│   │               │   ├── KhuNghiDuongRequest.java
│   │               │   ├── LoginRequest.java
│   │               │   ├── PasswordChangeRequest.java
│   │               │   ├── QuyDoiRequest.java
│   │               │   ├── RoomRequest.java
│   │               │   ├── RutTienRequest.java
│   │               │   ├── UpdatePhongRequest.java
│   │               │   ├── UpdateUtilitiesRequest.java
│   │               │   └── UserRegistrationDTO.java
│   │               └── respsonse
│   │               │   ├── HoaDonResponse.java
│   │               │   ├── LoginResponse.java
│   │               │   ├── PasswordChangeResponse.java
│   │               │   ├── ResortSearchResponse.java
│   │               │   ├── ServiceWithStatusDTO.java
│   │               │   ├── ServicesOfResortResponse.java
│   │               │   └── TienIchResponse.java
│   │           ├── repository
│   │               ├── AccountAssignRoleRepository.java
│   │               ├── AccountRepo.java
│   │               ├── AccountRoleGroupRepo.java
│   │               ├── AccountTokenRepo.java
│   │               ├── ChiTietDatPhongRepository.java
│   │               ├── CustomerRepository.java
│   │               ├── DanhGiaRepository.java
│   │               ├── DatPhongRepository.java
│   │               ├── DichVuMacDinhRepository.java
│   │               ├── DichVuYeuCauRepository.java
│   │               ├── DoiDiemRepository.java
│   │               ├── DoiTacRepository.java
│   │               ├── GiamGiaRepository.java
│   │               ├── GoiDaiPhongRepository.java
│   │               ├── HinhPhongRepo.java
│   │               ├── HoaDonJPA.java
│   │               ├── HoaDonRepository.java
│   │               ├── KhachHangRepository.java
│   │               ├── KhoMaGiamGiaRepository.java
│   │               ├── KhuNghiDuongRepo.java
│   │               ├── LichSuRutTienRepository.java
│   │               ├── LoaiPhongRepo.java
│   │               ├── PartnerRegistrationDTO.java
│   │               ├── PhongRepository.java
│   │               ├── PhuongRepository.java
│   │               ├── QuanRepository.java
│   │               ├── RoomTypeRepository.java
│   │               ├── RoomTypeRepositoryImpl.java
│   │               ├── ServicesOfResortRepository.java
│   │               ├── ServicesRepository.java
│   │               ├── ThanhPhoRepository.java
│   │               ├── ThoiGianPhongBanRepository.java
│   │               ├── TienIchRepo.java
│   │               ├── UserRepo.java
│   │               ├── UtilitiesOfRoomTypeRepository.java
│   │               ├── elastic
│   │               │   └── ResortElasticRepo.java
│   │               └── jdbc
│   │               │   ├── JdbcDanhGiaRepository.java
│   │               │   ├── JdbcResortRepository.java
│   │               │   └── JdbcRoomType.java
│   │           ├── service
│   │               ├── AccountService.java
│   │               ├── BookingListService.java
│   │               ├── ChiTietDatPhongService.java
│   │               ├── CloudinaryService.java
│   │               ├── CustomerService.java
│   │               ├── DanhGiaService.java
│   │               ├── DatPhongService.java
│   │               ├── DichVuMacDinhService.java
│   │               ├── DiemService.java
│   │               ├── DoiTacService.java
│   │               ├── EmailService.java
│   │               ├── GoiDatPhongService.java
│   │               ├── HinhPhongService.java
│   │               ├── HoaDonService.java
│   │               ├── KhachHangService.java
│   │               ├── KhoMaGiamGiaService.java
│   │               ├── KhuNghiDuongService.java
│   │               ├── LichSuRutTienService.java
│   │               ├── LoaiPhongService.java
│   │               ├── MaGiamGiaService.java
│   │               ├── MailService.java
│   │               ├── MailServiceImpl.java
│   │               ├── MomoService.java
│   │               ├── PhongService.java
│   │               ├── ResortService.java
│   │               ├── RoomTypeDetailServices.java
│   │               ├── ServicesOfResortService.java
│   │               ├── ServicesService.java
│   │               ├── ThoiGianPhongBanService.java
│   │               ├── ThongTinTaiKhoanService.java
│   │               ├── TienIchService.java
│   │               ├── UserService.java
│   │               ├── VerifyTokenService.java
│   │               ├── ZalopayService.java
│   │               └── elastic
│   │               │   └── ResortElasticService.java
│   │           └── utils
│   │               ├── CookieUtils.java
│   │               ├── HMACUtil.java
│   │               └── HexStringUtil.java
└── resources
│   ├── application.properties
│   ├── static
│       ├── css
│       │   ├── AdminView
│       │   │   ├── CustomerAccount
│       │   │   │   └── customerAccount.css
│       │   │   ├── PartnerAccount
│       │   │   │   ├── partnerAccount.css
│       │   │   │   └── partnerApproval.css
│       │   │   └── adminDashboard.css
│       │   ├── CustomerView
│       │   │   ├── BookingDetail.css
│       │   │   ├── BookingHistory.css
│       │   │   ├── ChangePassword.css
│       │   │   ├── DiscountCodes.css
│       │   │   ├── GioHang.css
│       │   │   ├── Header.css
│       │   │   ├── PointRedemption.css
│       │   │   ├── ResortDetail.css
│       │   │   ├── SearchBar.css
│       │   │   ├── SearchResult-fixed.css
│       │   │   └── SearchResult.css
│       │   └── PartnerDashboard
│       │   │   └── ResortListStyle.css
│       └── js
│       │   ├── AdminView
│       │       ├── CustomerAccount
│       │       │   └── customerAccount.js
│       │       └── PartnerAccount
│       │       │   ├── partnerAccount.js
│       │       │   └── partnerApproval.js
│       │   ├── CustomerView
│       │       ├── BookingDetail.js
│       │       ├── BookingHistory.js
│       │       ├── ChangePassword.js
│       │       ├── DiscountCodes.js
│       │       ├── GioHang.js
│       │       ├── Payment.js
│       │       ├── PointRedemption.js
│       │       ├── ResortDetail.js
│       │       └── SearchResult.js
│       │   ├── resort-list.js
│       │   ├── room-type.js
│       │   └── signin.js
│   └── templates
│       ├── AdminView
│           ├── AdminDashboard.html
│           ├── CustomerAccount
│           │   └── CustomerAccount.html
│           ├── PartnerAccount
│           │   ├── PartnerAccount.html
│           │   └── PartnerApproval.html
│           └── Withdraw
│           │   └── Withdraw.html
│       ├── CustomerView
│           ├── BookingDetail.html
│           ├── BookingHistory.html
│           ├── ChangePassword.html
│           ├── DiscountCodes.html
│           ├── GioHang.html
│           ├── PointRedemption.html
│           ├── Profile.html
│           ├── ResortDetail.html
│           ├── SearchResort.html
│           ├── SearchResult.html
│           └── components
│           │   ├── Header.html
│           │   └── SearchBar.html
│       ├── EmailVerifiedSucces.html
│       ├── GetStarted.html
│       ├── MailHTML.html
│       ├── MyRole.html
│       ├── PartnerSignin.html
│       ├── PartnerSignup.html
│       ├── PartnerView
│           ├── BookingOfferManagement
│           │   └── BookingOfferManagement.html
│           ├── PartnerDashBoard.html
│           ├── Report
│           │   └── ReportDashboard.html
│           ├── RoomManagement
│           │   └── RoomManagement.html
│           ├── RoomTypeManagement.html
│           └── Withdraw
│           │   └── Withdraw.html
│       ├── PaymentSuccess.html
│       ├── Profile
│           ├── CustomerProfile.html
│           └── avatar.png
│       ├── signin.html
│       └── signup.html
└── test
└── java
└── com
└── example
└── IS216_Dlegent
└── Is216DlegentApplicationTests.java
