
function setup_widgets_desktop() {
    $.fn.jarvisWidgets && enableJarvisWidgets && $("#widget-grid").jarvisWidgets({grid: "article", widgets: ".jarviswidget", localStorage: !0, deleteSettingsKey: "#deletesettingskey-options", settingsKeyLabel: "Reset settings?", deletePositionKey: "#deletepositionkey-options", positionKeyLabel: "Reset position?", sortable: !0, buttonsHidden: !1, toggleButton: !0, toggleClass: "fa fa-minus | fa fa-plus", toggleSpeed: 200, onToggle: function() {
        }, deleteButton: !0, deleteClass: "fa fa-times", deleteSpeed: 200, onDelete: function() {
        }, editButton: !0, editPlaceholder: ".jarviswidget-editbox", editClass: "fa fa-cog | fa fa-save", editSpeed: 200, onEdit: function() {
        }, colorButton: !0, fullscreenButton: !0, fullscreenClass: "fa fa-expand | fa fa-compress", fullscreenDiff: 3, onFullscreen: function() {
        }, customButton: !1, customClass: "folder-10 | next-10", customStart: function() {
            alert("Hello you, this is a custom button...")
        }, customEnd: function() {
            alert("bye, till next time...")
        }, buttonOrder: "%refresh% %custom% %edit% %toggle% %fullscreen% %delete%", opacity: 1, dragHandle: "> header", placeholderClass: "jarviswidget-placeholder", indicator: !0, indicatorTime: 600, ajax: !0, timestampPlaceholder: ".jarviswidget-timestamp", timestampFormat: "Last update: %m%/%d%/%y% %h%:%i%:%s%", refreshButton: !0, refreshButtonClass: "fa fa-refresh", labelError: "Sorry but there was a error:", labelUpdated: "Last Update:", labelRefresh: "Refresh", labelDelete: "Delete widget:", afterLoad: function() {
        }, rtl: !1, onChange: function() {
        }, onSave: function() {
        }, ajaxnav: $.navAsAjax})
}
function setup_widgets_mobile() {
    enableMobileWidgets && enableJarvisWidgets && setup_widgets_desktop()
}
function loadScript(a, b) {
    if (jsArray[a])
        b && b();
    else {
        jsArray[a] = !0;
        var c = document.getElementsByTagName("body")[0], d = document.createElement("script");
        d.type = "text/javascript", d.src = a, d.onload = b, c.appendChild(d)
    }
}
$.root_ = $("body"), $.intervalArr = [];
var calc_navbar_height = function() {
    var a = null;
    return $("#header").length && (a = $("#header").height()), null === a && (a = $('<div id="header"></div>').height()), null === a ? 49 : a
}, navbar_height = calc_navbar_height, shortcut_dropdown = $("#shortcut"), bread_crumb = $("#ribbon ol.breadcrumb"), topmenu = !1, thisDevice = null, ismobile = /iphone|ipad|ipod|android|blackberry|mini|windows\sce|palm/i.test(navigator.userAgent.toLowerCase()), jsArray = {}, initApp = function(a) {
    return a.addDeviceType = function() {
        return ismobile ? ($.root_.addClass("mobile-detected"), thisDevice = "mobile", fastClick ? ($.root_.addClass("needsclick"), FastClick.attach(document.body), !1) : void 0) : ($.root_.addClass("desktop-detected"), thisDevice = "desktop", !1)
    }, a.menuPos = function() {
        ($.root_.hasClass("menu-on-top") || "top" == localStorage.getItem("sm-setmenu")) && (topmenu = !0, $.root_.addClass("menu-on-top"))
    }, a.SmartActions = function() {
        var a = {userLogout: function(a) {
                function b() {
                    window.location = a.attr("href")
                }
            }, launchFullscreen: function(a) {
                $.root_.hasClass("full-screen") ? ($.root_.removeClass("full-screen"), document.exitFullscreen ? document.exitFullscreen() : document.mozCancelFullScreen ? document.mozCancelFullScreen() : document.webkitExitFullscreen && document.webkitExitFullscreen()) : ($.root_.addClass("full-screen"), a.requestFullscreen ? a.requestFullscreen() : a.mozRequestFullScreen ? a.mozRequestFullScreen() : a.webkitRequestFullscreen ? a.webkitRequestFullscreen() : a.msRequestFullscreen && a.msRequestFullscreen())
            }, minifyMenu: function(a) {
                $.root_.hasClass("menu-on-top") || ($.root_.toggleClass("minified"), $.root_.removeClass("hidden-menu"), $("html").removeClass("hidden-menu-mobile-lock"), a.effect("highlight", {}, 500))
            }, toggleMenu: function() {
                $.root_.hasClass("menu-on-top") ? $.root_.hasClass("menu-on-top") && $.root_.hasClass("mobile-view-activated") && ($("html").toggleClass("hidden-menu-mobile-lock"), $.root_.toggleClass("hidden-menu"), $.root_.removeClass("minified")) : ($("html").toggleClass("hidden-menu-mobile-lock"), $.root_.toggleClass("hidden-menu"), $.root_.removeClass("minified"))
            }, toggleShortcut: function() {
                function a() {
                    shortcut_dropdown.animate({height: "hide"}, 300, "easeOutCirc"), $.root_.removeClass("shortcut-on")
                }
                function b() {
                    shortcut_dropdown.animate({height: "show"}, 200, "easeOutCirc"), $.root_.addClass("shortcut-on")
                }
                shortcut_dropdown.is(":visible") ? a() : b(), shortcut_dropdown.find("a").click(function(b) {
                    b.preventDefault(), window.location = $(this).attr("href"), setTimeout(a, 300)
                }), $(document).mouseup(function(b) {
                    shortcut_dropdown.is(b.target) || 0 !== shortcut_dropdown.has(b.target).length || a()
                })
            }};
        $.root_.on("click", '[data-action="userLogout"]', function(b) {
            var c = $(this);
            a.userLogout(c), b.preventDefault(), c = null
        }), $.root_.on("click", '[data-action="resetWidgets"]', function(b) {
            var c = $(this);
            a.resetWidgets(c), b.preventDefault(), c = null
        }), $.root_.on("click", '[data-action="launchFullscreen"]', function(b) {
            a.launchFullscreen(document.documentElement), b.preventDefault()
        }), $.root_.on("click", '[data-action="minifyMenu"]', function(b) {
            var c = $(this);
            a.minifyMenu(c), b.preventDefault(), c = null
        }), $.root_.on("click", '[data-action="toggleMenu"]', function(b) {
            a.toggleMenu(), b.preventDefault()
        }), $.root_.on("click", '[data-action="toggleShortcut"]', function(b) {
            a.toggleShortcut(), b.preventDefault()
        })
    }, a.leftNav = function() {
        topmenu || $("nav ul").jarvismenu({accordion: !0, speed: menu_speed, closedSign: '<em class="fa fa-plus-square-o"></em>', openedSign: '<em class="fa fa-minus-square-o"></em>'})
    }, a.domReadyMisc = function() {
        $("[rel=tooltip]").length && $("[rel=tooltip]").tooltip(), $("#search-mobile").click(function() {
            $.root_.addClass("search-mobile")
        }), $("#cancel-search-js").click(function() {
            $.root_.removeClass("search-mobile")
        }), $("#activity").click(function(a) {
            var b = $(this);
            b.find(".badge").hasClass("bg-color-red") && (b.find(".badge").removeClassPrefix("bg-color-"), b.find(".badge").text("0")), b.next(".ajax-dropdown").is(":visible") ? (b.next(".ajax-dropdown").fadeOut(150), b.removeClass("active")) : (b.next(".ajax-dropdown").fadeIn(150), b.addClass("active"));
            var c = b.next(".ajax-dropdown").find(".btn-group > .active > input").attr("id");
            b = null, c = null, a.preventDefault()
        }), $('input[name="activity"]').change(function() {
        }), $(document).mouseup(function(a) {
            $(".ajax-dropdown").is(a.target) || 0 !== $(".ajax-dropdown").has(a.target).length || ($(".ajax-dropdown").fadeOut(150), $(".ajax-dropdown").prev().removeClass("active"))
        }), $("button[data-btn-loading]").on("click", function() {
            var a = $(this);
            a.button("loading"), setTimeout(function() {
                a.button("reset")
            }, 3e3), $this = null
        }), $this = $("#activity > .badge"), parseInt($this.text()) > 0 && ($this.addClass("bg-color-red bounceIn animated"), $this = null)
    }, a
}({});
initApp.addDeviceType(), initApp.menuPos(), jQuery(document).ready(function() {
    initApp.SmartActions(), initApp.leftNav(), initApp.domReadyMisc()
}), function(a, b, c) {
    function d() {
        e = b[h](function() {
            f.each(function() {
                var b, c, d = a(this), e = a.data(this, j);
                try {
                    b = d.width()
                } catch (f) {
                    b = d.width
                }
                try {
                    c = d.height()
                } catch (f) {
                    c = d.height
                }
                (b !== e.w || c !== e.h) && d.trigger(i, [e.w = b, e.h = c])
            }), d()
        }, g[k])
    }
    var e, f = a([]), g = a.resize = a.extend(a.resize, {}), h = "setTimeout", i = "resize", j = i + "-special-event", k = "delay", l = "throttleWindow";

    g[l] = !0, a.event.special[i] = {setup: function() {
            if (!g[l] && this[h])
                return!1;
            var b = a(this);
            f = f.add(b);
            try {
                a.data(this, j, {w: b.width(), h: b.height()})
            } catch (c) {
                a.data(this, j, {w: b.width, h: b.height})
            }
            1 === f.length && d()
        }, teardown: function() {
            if (!g[l] && this[h])
                return!1;
            var b = a(this);
            f = f.not(b), b.removeData(j), f.length || clearTimeout(e)
        }, add: function(b) {
            function d(b, d, f) {
                var g = a(this), h = a.data(this, j);
                h.w = d !== c ? d : g.width(), h.h = f !== c ? f : g.height(), e.apply(this, arguments)
            }
            if (!g[l] && this[h])
                return!1;
            var e;
            return a.isFunction(b) ? (e = b, d) : (e = b.handler, void(b.handler = d))
        }}
}(jQuery, this), $("#main").resize(function() {
    $(window).width() < 979 ? ($.root_.addClass("mobile-view-activated"), $.root_.removeClass("minified")) : $.root_.hasClass("mobile-view-activated") && $.root_.removeClass("mobile-view-activated")
});
var ie = function() {
    for (var a, b = 3, c = document.createElement("div"), d = c.getElementsByTagName("i"); c.innerHTML = "<!--[if gt IE " + ++b + "]><i></i><![endif]-->", d[0]; )
        ;
    return b > 4 ? b : a
}();
$.navAsAjax && ($("nav").length , $(document).on("click", 'nav a[href!="#"]', function(a) {
    a.preventDefault();
    var b = $(a.currentTarget);
    b.parent().hasClass("active") || b.attr("target") || ($.root_.hasClass("mobile-view-activated") ? ($.root_.removeClass("hidden-menu"), $("html").removeClass("hidden-menu-mobile-lock"), window.setTimeout(function() {
        window.location.search ? window.location.href = window.location.href.replace(window.location.search, "").replace(window.location.hash, "") + "#" + b.attr("href") : window.location.hash = b.attr("href")
    }, 150)) : window.location.search ? window.location.href = window.location.href.replace(window.location.search, "").replace(window.location.hash, "") + "#" + b.attr("href") : window.location.hash = b.attr("href"))
}), $(document).on("click", 'nav a[target="_blank"]', function(a) {
    a.preventDefault();
    var b = $(a.currentTarget);
    window.open(b.attr("href"))
}), $(document).on("click", 'nav a[target="_top"]', function(a) {
    a.preventDefault();
    var b = $(a.currentTarget);
    window.location = b.attr("href")
}), $(document).on("click", 'nav a[href="#"]', function(a) {
    a.preventDefault()
}), $(window).on("hashchange", function() {
})), $("body").on("click", function(a) {
    $('[rel="popover"]').each(function() {
        $(this).is(a.target) || 0 !== $(this).has(a.target).length || 0 !== $(".popover").has(a.target).length || $(this).popover("hide")
    })
});