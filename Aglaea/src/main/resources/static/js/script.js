(function($) {

  "use strict";

  const tabs = document.querySelectorAll('[data-tab-target]')
  const tabContents = document.querySelectorAll('[data-tab-content]')

  tabs.forEach(tab => {
    tab.addEventListener('click', () => {
      const target = document.querySelector(tab.dataset.tabTarget)
      tabContents.forEach(tabContent => {
        tabContent.classList.remove('active')
      })
      tabs.forEach(tab => {
        tab.classList.remove('active')
      })
      tab.classList.add('active')
      target.classList.add('active')
    })
  });

  // Responsive Navigation with Button

  const hamburger = document.querySelector(".hamburger");
  const navMenu = document.querySelector(".menu-list");

  hamburger.addEventListener("click", mobileMenu);

  function mobileMenu() {
      hamburger.classList.toggle("active");
      navMenu.classList.toggle("responsive");
  }

  const navLink = document.querySelectorAll(".nav-link");

  navLink.forEach(n => n.addEventListener("click", closeMenu));

  function closeMenu() {
      hamburger.classList.remove("active");
      navMenu.classList.remove("responsive");
  }

  var initScrollNav = function() {
    var scroll = $(window).scrollTop();

    if (scroll >= 200) {
      $('#header').addClass("fixed-top");
    }else{
      $('#header').removeClass("fixed-top");
    }
  }

  $(window).scroll(function() {    
    initScrollNav();
  }); 

  $(document).ready(function(){
    initScrollNav();
    
    Chocolat(document.querySelectorAll('.image-link'), {
        imageSize: 'contain',
        loop: true,
    })

    $('#header-wrap').on('click', '.search-toggle', function(e) {
      var selector = $(this).data('selector');

      $(selector).toggleClass('show').find('.search-input').focus();
      $(this).toggleClass('active');

      e.preventDefault();
    });


    // close when click off of container
    $(document).on('click touchstart', function (e){
      if (!$(e.target).is('.search-toggle, .search-toggle *, #header-wrap, #header-wrap *')) {
        $('.search-toggle').removeClass('active');
        $('#header-wrap').removeClass('show');
      }
    });

    $('.main-slider').slick({
        autoplay: false,
        autoplaySpeed: 4000,
        fade: true,
        dots: true,
        prevArrow: $('.prev'),
        nextArrow: $('.next'),
    }); 

    $('.product-grid').slick({
        slidesToShow: 4,
        slidesToScroll: 1,
        autoplay: false,
        autoplaySpeed: 2000,
        dots: true,
        arrows: false,
        responsive: [
          {
            breakpoint: 1400,
            settings: {
              slidesToShow: 3,
              slidesToScroll: 1
            }
          },
          {
            breakpoint: 999,
            settings: {
              slidesToShow: 2,
              slidesToScroll: 1
            }
          },
          {
            breakpoint: 660,
            settings: {
              slidesToShow: 1,
              slidesToScroll: 1
            }
          }
        ]
    });

    AOS.init({
      duration: 1200,
      once: true,
    })

    jQuery('.stellarnav').stellarNav({
      theme: 'plain',
      closingDelay: 250,
      // mobileMode: false,
    });

  }); // End of a document


})(jQuery);

let allBooks = []; // lưu toàn bộ sách
const booksPerPage = 8;
let currentPage = 1;

function renderBooksPage(page) {
  const bookList = $("#bookList");
  bookList.empty();

  if (!allBooks || allBooks.length === 0) {
    bookList.append(`<div class="col-12 text-center">Không có sách nào</div>`);
    return;
  }

  const startIndex = (page - 1) * booksPerPage;
  const endIndex = Math.min(startIndex + booksPerPage, allBooks.length);

  for (let i = startIndex; i < endIndex; i++) {
    const book = allBooks[i];
    const bookHtml = `
      <div class="col-md-3">
        <div class="product-item">
          <figure class="product-style">
            <img src="${book.imageUrl}" alt="Books" class="product-item"/>
            <button type="button" class="add-to-cart">Add to Cart</button>
          </figure>
          <figcaption>
            <h3>${book.title}</h3>
            <span>${book.author}</span>
          </figcaption>
        </div>
      </div>
    `;
    bookList.append(bookHtml);
  }

  renderPagination();
}

function renderPagination() {
  const pagination = $("#pagination");
  pagination.empty();

  const totalPages = Math.ceil(allBooks.length / booksPerPage);
  if (totalPages <= 1) return; // ko cần hiện phân trang

  const prevClass = currentPage === 1 ? "disabled" : "";
  pagination.append(`<li class="page-item ${prevClass}"><a class="page-link" href="#" data-page="${currentPage - 1}">Previous</a></li>`);

  for (let i = 1; i <= totalPages; i++) {
    const activeClass = currentPage === i ? "active" : "";
    pagination.append(`<li class="page-item ${activeClass}"><a class="page-link" href="#" data-page="${i}">${i}</a></li>`);
  }

  const nextClass = currentPage === totalPages ? "disabled" : "";
  pagination.append(`<li class="page-item ${nextClass}"><a class="page-link" href="#" data-page="${currentPage + 1}">Next</a></li>`);
}

function loadBooks(category) {
  $.ajax({
    url: '/api/books',
    type: 'GET',
    data: {
      category: category
    },
    success: function (data) {
      allBooks = data.sort((a, b) => b.id - a.id); // sắp xếp mới nhất lên đầu
      currentPage = 1;
      renderBooksPage(currentPage);
    },
    error: function () {
      alert("Lỗi tải sách!");
    }
  });
}

$(document).ready(function () {
  $(".category-link").click(function (e) {
    e.preventDefault();

    const category = $(this).data("category");
    loadBooks(category);

    $(".category-link").removeClass("active");
    $(this).addClass("active");
  });

  // Thêm container phân trang ngay sau danh sách sách
  $("#bookList").after('<nav><ul class="pagination justify-content-center" id="pagination"></ul></nav>');

  // Bắt sự kiện click nút phân trang
  $(document).on("click", ".page-link", function(e) {
    e.preventDefault();
    const page = parseInt($(this).data("page"));
    const totalPages = Math.ceil(allBooks.length / booksPerPage);

    if (page >= 1 && page <= totalPages && page !== currentPage) {
      currentPage = page;
      renderBooksPage(currentPage);
      $('html, body').animate({ scrollTop: $("#bookList").offset().top }, 300);
    }
  });

  // Load mặc định tất cả sách
  loadBooks(null);
});


function confirmLogout(event) {
  if (!confirm("Bạn có chắc chắn muốn đăng xuất không?")) {
    event.preventDefault(); // Ngăn chặn việc gửi form nếu người dùng chọn "Hủy"
    return false;
  }
  return true;
}