$(function () {
  $(".bd-slider").slick({
    infinite: true,
    slidesToShow: 1,
    slidesToScroll: 1,
    dots: true,
    arrows: true,
    autoplaySpeed: 1000,
    pauseOnHover: true,
    prevArrow: "<i class='xi-angle-left prevBtn'></i>",
    nextArrow: "<i class='xi-angle-right nextBtn'></i>"
  });
});