(function ($) {
  $.fn.gallerybox = function(options) {
    var
      defaults = {
        bgColor: '#000',
        bgOpacity: 0.95,
        closeText: 'CLOSE'
      },
      settings = $.extend({}, defaults, options),
      images = this,

      // Open gallerybox
      open = function(img) {
        // Create #gallerybox element
        $('<div id="gallerybox"></div>')
          .appendTo('body');

        // Create span element for closing GalleryBox
        $('<span>' + settings.closeText + '</span>')
          .delay('fast')
          .fadeIn()
          .appendTo('#gallerybox')
          .click(close);

        // Create #gb-overlay element
        $('<div id="gb-overlay"></div>')
          .css({
            backgroundColor: settings.bgColor,
            opacity: 0
          })
          .appendTo('#gallerybox')
          .animate({
            'opacity' : settings.bgOpacity
          }, 'slow');

        // Create #gb-big element
        $('<div id="gb-big"></div>')
          .delay()
          .appendTo('#gallerybox');

        // Create #gb-list element
        $('<div id="gb-list"></div>')
          .appendTo('#gallerybox');

        // Load images in imagelist
        $.each(images, function() {
          $('<img>')
            .attr('src', $(this).attr('src'))
            .hide()
            .load(function() {
              $(this)
                .delay('fast')
                .fadeIn();
            })
            .appendTo('#gb-list')
            .click(function() {
              loadImage(this);
            });
        });

        // Create scrollbuttons
        $('<div class="left">&lt;</div><div class="right">&gt;</div>')
          .hide()
          .delay('slow')
          .fadeIn()
          .appendTo('#gb-list')
          .click(function() {
            scrollList(this);
          });

        // Load big image
        if (img) {
          loadImage(img);
        }
      },

      // Close gallerybox
      close = function() {
        $('#gallerybox').fadeOut('slow', function() {
          $(this).remove();
        });
      },

      loadImage = function(img) {
        $('#gb-big')
          .fadeOut(function() {
            // Remove current image
            $('#gb-big img')
            .remove();

            // Create new image element
            $('<img>')
              .attr('src', $(img).attr('src'))
              .css({
                'max-height': ($(window).height() - 150) * 0.9, 
                'max-width': $(window).width() * 0.9
              })
              .load(function() {
                $('#gb-big')
                  .css({
                    'top': ($(window).height() - $('#gb-big').height() - 150) / 2,
                    'left': ($(window).width() - $('#gb-big').width()) / 2
                  })
                  .fadeIn();
              })
              .appendTo('#gb-big');
          });
      },

      scrollList = function(elem) {
        var
          dir = $(elem).hasClass('left') ? -1 : 1,
          curPos = $('#gb-list').scrollLeft(),
          newPos = curPos + dir * $(window).width() * 0.9,
          endPos = $('#gb-list')[0].scrollWidth - $(window).width(),
          moveTo = dir === -1 ? (newPos > 0 ? newPos : 0) : (newPos < endPos ? newPos : endPos);

        if (curPos !== moveTo) {
          $('#gb-list').animate({
            scrollLeft: moveTo
          }, 1000);
        }
      };

    // Change cursor to pointer
    images.css('cursor', 'pointer');

    // On image click
    images.click(function() {
      open(this);
    });
  };
}(jQuery));
