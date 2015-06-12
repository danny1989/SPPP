(function($) {
    $.fn.fitDataTable = function(options) {
        var actualy = this;
        // This is the easiest way to have default options.
        var settings = $.extend({
            // These are the defaults.
            record: 0,
            rowsPerPage: 10,
            event: ''
        }, options);

        if ($("div", this).first()[0]) {
            $("div", this).first().after(createFooterPagination());
        } else if ($("table", this).first()[0]) {
            $("table", this).first().after(createFooterPagination());
        } else {
            console.log('NO EXISTE NI DIV, NI TABLE');
        }

        function createFooterPagination() {
            var totalPages = Math.ceil(settings.record / settings.rowsPerPage);
            var concat = "<div class=\"row\">";
            //concat += "<div class=\"col-lg-3\"><span class=\"input-group-addon\">Número Registros: " + settings.record + "</span></div>";
            concat += "<div class=\"col-lg-12\" align=\"center\">";
            concat += "<ul class=\"pagination\" style=\"padding: 0px; margin:0px;\">";
            concat += "<li><a href=\"javascript:void(0);\"><i class=\"fa fa-arrow-left\"></i></a></li>";
            for (i = 1; i <= totalPages; i++) {
                if (i === 1) {
                    concat += "<li class=\"active\"><a href=\"javascript:void(0)\" onclick=\"" + settings.event + "(" + settings.rowsPerPage + "," + i + ")\">" + i + "</a></li>";
                } else {
                    concat += "<li ><a href=\"javascript:void(0)\" onclick=\"" + settings.event + "(" + settings.rowsPerPage + "," + i + ")\">" + i + "</a></li>";
                }
            }
            concat += "<li><a href=\"javascript:void(0);\"><i class=\"fa fa-arrow-right\"></i></a></li>";
            concat += "</ul></div>";
            return concat;
        }
        
        $($("li", this)).bind('click', function() {
            $("li.active", actualy).removeClass('active');
            $(this).addClass('active');
        });

        return settings;
    };

    $.fn.fitButton = function(options) {
        
    };
}(jQuery));

