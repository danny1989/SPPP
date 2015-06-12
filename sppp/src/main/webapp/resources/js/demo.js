PrimeFaces.widget.AutoComplete = PrimeFaces.widget.BaseWidget.extend({init: function(a) {
        this._super(a);
        this.panelId = this.jqId + "_panel";
        this.input = $(this.jqId + "_input");
        this.hinput = $(this.jqId + "_hinput");
        this.panel = this.jq.children(this.panelId);
        this.dropdown = this.jq.children(".ui-button");
        this.disabled = this.input.is(":disabled");
        this.active = true;
        this.cfg.pojo = this.hinput.length == 1;
        this.cfg.minLength = this.cfg.minLength != undefined ? this.cfg.minLength : 1;
        this.cfg.cache = this.cfg.cache || false;
        if (this.cfg.cache) {
            this.initCache()
        }
        this.input.data(PrimeFaces.CLIENT_ID_DATA, this.id);
        this.hinput.data(PrimeFaces.CLIENT_ID_DATA, this.id);
        if (!this.disabled) {
            if (this.cfg.multiple) {
                this.setupMultipleMode();
                this.multiItemContainer.data("primefaces-overlay-target", true).find("*").data("primefaces-overlay-target", true)
            } else {
                PrimeFaces.skinInput(this.input);
                this.input.data("primefaces-overlay-target", true).find("*").data("primefaces-overlay-target", true);
                this.dropdown.data("primefaces-overlay-target", true).find("*").data("primefaces-overlay-target", true)
            }
            this.bindStaticEvents();
            if (this.cfg.behaviors) {
                PrimeFaces.attachBehaviors(this.input, this.cfg.behaviors)
            }
            if (this.cfg.forceSelection) {
                this.setupForceSelection()
            }
            this.appendPanel();
            if (this.cfg.itemtip) {
                this.itemtip = $('<div id="' + this.id + '_itemtip" class="ui-autocomplete-itemtip ui-state-highlight ui-widget ui-corner-all ui-shadow"></div>').appendTo(document.body);
                this.cfg.itemtipMyPosition = this.cfg.itemtipMyPosition || "left top";
                this.cfg.itemtipAtPosition = this.cfg.itemtipAtPosition || "right bottom";
                this.cfg.checkForScrollbar = (this.cfg.itemtipAtPosition.indexOf("right") !== -1)
            }
        }
    }, appendPanel: function() {
        var a = this.cfg.appendTo ? PrimeFaces.expressions.SearchExpressionFacade.resolveComponentsAsSelector(this.cfg.appendTo) : $(document.body);
        if (!a.is(this.jq)) {
            a.children(this.panelId).remove();
            this.panel.appendTo(a)
        }
    }, initCache: function() {
        this.cache = {};
        var a = this;
        this.cacheTimeout = setInterval(function() {
            a.clearCache()
        }, this.cfg.cacheTimeout)
    }, clearCache: function() {
        this.cache = {}
    }, setupMultipleMode: function() {
        var b = this;
        this.multiItemContainer = this.jq.children("ul");
        this.inputContainer = this.multiItemContainer.children(".ui-autocomplete-input-token");
        this.multiItemContainer.hover(function() {
            $(this).addClass("ui-state-hover")
        }, function() {
            $(this).removeClass("ui-state-hover")
        }).click(function() {
            b.input.focus()
        });
        this.input.focus(function() {
            b.multiItemContainer.addClass("ui-state-focus")
        }).blur(function(c) {
            b.multiItemContainer.removeClass("ui-state-focus")
        });
        var a = "> li.ui-autocomplete-token > .ui-autocomplete-token-icon";
        this.multiItemContainer.off("click", a).on("click", a, null, function(c) {
            b.removeItem(c, $(this).parent())
        })
    }, bindStaticEvents: function() {
        var a = this;
        this.bindKeyEvents();
        this.dropdown.mouseover(function() {
            if (!a.disabled) {
                $(this).addClass("ui-state-hover")
            }
        }).mouseout(function() {
            if (!a.disabled) {
                $(this).removeClass("ui-state-hover")
            }
        }).mousedown(function() {
            if (!a.disabled && a.active) {
                $(this).addClass("ui-state-active")
            }
        }).mouseup(function() {
            if (!a.disabled && a.active) {
                $(this).removeClass("ui-state-active");
                a.search("");
                a.input.focus()
            }
        }).focus(function() {
            $(this).addClass("ui-state-focus")
        }).blur(function() {
            $(this).removeClass("ui-state-focus")
        }).keydown(function(d) {
            var c = $.ui.keyCode;
            if (d.which == c.ENTER || d.which == c.NUMPAD_ENTER) {
                a.search("");
                a.input.focus();
                d.preventDefault()
            }
        });
        var b;
        $(document.body).bind("mousedown.ui-autocomplete", function(c) {
            if (a.panel.is(":hidden")) {
                return
            }
            b = a.panel.offset();
            if (c.target === a.input.get(0)) {
                return
            }
            if (c.pageX < b.left || c.pageX > b.left + a.panel.width() || c.pageY < b.top || c.pageY > b.top + a.panel.height()) {
                a.hide()
            }
        });
        this.resizeNS = "resize." + this.id;
        $(window).off(this.resizeNS).on(this.resizeNS, function(c) {
            if (a.panel.is(":visible")) {
                a.alignPanel()
            }
        })
    }, bindKeyEvents: function() {
        var a = this;
        this.input.keyup(function(h) {
            var g = $.ui.keyCode, c = h.which, f = true;
            if (c === g.ENTER || c === g.NUMPAD_ENTER) {
                if (a.timeout) {
                    clearTimeout(a.timeout)
                }
                f = false
            } else {
                if (c === g.ESCAPE) {
                    a.hide();
                    f = false
                } else {
                    if ((h.ctrlKey && c === 65) || (h.ctrlKey && c === 67) || c === g.UP || c === g.LEFT || c === g.DOWN || c === g.RIGHT || c === g.TAB || c === 16 || c === g.HOME || c === g.END || c === 18 || c === 17 || (c >= 112 && c <= 123)) {
                        f = false
                    } else {
                        if (a.cfg.pojo && !a.cfg.multiple) {
                            a.hinput.val($(this).val())
                        }
                    }
                }
            }
            if (f) {
                var d = a.input.val();
                if (!d.length) {
                    a.hide()
                }
                if (d.length >= a.cfg.minLength) {
                    if (a.timeout) {
                        clearTimeout(a.timeout)
                    }
                    var b = a.cfg.delay;
                    if (d != "" && (c == g.BACKSPACE || c == g.DELETE)) {
                        b = a.cfg.deletionDelay
                    }
                    a.timeout = setTimeout(function() {
                        a.search(d)
                    }, b)
                }
            }
        }).keydown(function(g) {
            var f = $.ui.keyCode;
            if (a.panel.is(":visible")) {
                var d = a.items.filter(".ui-state-highlight");
                switch (g.which) {
                    case f.UP:
                        var c = d.length == 0 ? a.items.eq(0) : d.prevAll(".ui-autocomplete-item:first");
                        if (c.length == 1) {
                            d.removeClass("ui-state-highlight");
                            c.addClass("ui-state-highlight");
                            if (a.cfg.scrollHeight) {
                                PrimeFaces.scrollInView(a.panel, c)
                            }
                            if (a.cfg.itemtip) {
                                a.showItemtip(c)
                            }
                        }
                        g.preventDefault();
                        break;
                    case f.DOWN:
                        var b = d.length == 0 ? a.items.eq(0) : d.nextAll(".ui-autocomplete-item:first");
                        if (b.length == 1) {
                            d.removeClass("ui-state-highlight");
                            b.addClass("ui-state-highlight");
                            if (a.cfg.scrollHeight) {
                                PrimeFaces.scrollInView(a.panel, b)
                            }
                            if (a.cfg.itemtip) {
                                a.showItemtip(b)
                            }
                        }
                        g.preventDefault();
                        break;
                    case f.ENTER:
                    case f.NUMPAD_ENTER:
                        d.click();
                        g.preventDefault();
                        break;
                    case 18:
                    case 224:
                        break;
                    case f.TAB:
                        d.trigger("click");
                        a.hide();
                        break
                    }
            } else {
                if (g.which == f.TAB) {
                    if (a.timeout) {
                        clearTimeout(a.timeout)
                    }
                }
            }
        })
    }, bindDynamicEvents: function() {
        var a = this;
        this.items.bind("mouseover", function() {
            var b = $(this);
            if (!b.hasClass("ui-state-highlight")) {
                a.items.filter(".ui-state-highlight").removeClass("ui-state-highlight");
                b.addClass("ui-state-highlight");
                if (a.cfg.itemtip) {
                    a.showItemtip(b)
                }
            }
        }).bind("click", function(d) {
            var c = $(this), e = c.attr("data-item-value");
            if (a.cfg.multiple) {
                var b = '<li data-token-value="' + c.attr("data-item-value") + '"class="ui-autocomplete-token ui-state-active ui-corner-all ui-helper-hidden">';
                b += '<span class="ui-autocomplete-token-icon ui-icon ui-icon-close" />';
                b += '<span class="ui-autocomplete-token-label">' + c.attr("data-item-label") + "</span></li>";
                a.inputContainer.before(b);
                a.multiItemContainer.children(".ui-helper-hidden").fadeIn();
                a.input.val("").focus();
                a.hinput.append('<option value="' + e + '" selected="selected"></option>')
            } else {
                a.input.val(c.attr("data-item-label")).focus();
                if (a.cfg.pojo) {
                    a.hinput.val(e)
                }
            }
            a.invokeItemSelectBehavior(d, e);
            a.hide()
        })
    }, showItemtip: function(c) {
        var b = c.is("li") ? c.next(".ui-autocomplete-itemtip-content") : c.children("td:last");
        this.itemtip.html(b.html()).css({left: "", top: "", "z-index": ++PrimeFaces.zindex, width: b.outerWidth()}).position({my: this.cfg.itemtipMyPosition, at: this.cfg.itemtipAtPosition, of: c});
        if (this.cfg.checkForScrollbar) {
            if (this.panel.innerHeight() < this.panel.children(".ui-autocomplete-items").outerHeight(true)) {
                var a = this.panel.offset();
                this.itemtip.css("left", a.left + this.panel.outerWidth())
            }
        }
        this.itemtip.show()
    }, showSuggestions: function(c) {
        this.items = this.panel.find(".ui-autocomplete-item");
        this.bindDynamicEvents();
        var e = this, b = this.panel.is(":hidden");
        if (b) {
            this.show()
        } else {
            this.alignPanel()
        }
        if (this.items.length > 0) {
            var d = this.items.eq(0);
            d.addClass("ui-state-highlight");
            if (this.panel.children().is("ul") && c.length > 0) {
                this.items.each(function() {
                    var g = $(this), i = g.html(), f = new RegExp(PrimeFaces.escapeRegExp(c), "gi"), h = i.replace(f, '<span class="ui-autocomplete-query">$&</span>');
                    g.html(h)
                })
            }
            if (this.cfg.forceSelection) {
                this.currentItems = [];
                this.items.each(function(f, g) {
                    e.currentItems.push($(g).attr("data-item-label"))
                })
            }
            if (this.cfg.itemtip && d.length === 1) {
                this.showItemtip(d)
            }
        } else {
            if (this.cfg.emptyMessage) {
                var a = '<div class="ui-autocomplete-emptyMessage ui-widget">' + this.cfg.emptyMessage + "</div>";
                this.panel.html(a)
            } else {
                this.panel.hide()
            }
        }
    }, search: function(c) {
        if (c === undefined || c === null) {
            return
        }
        if (this.cfg.cache && this.cache[c]) {
            this.panel.html(this.cache[c]);
            this.showSuggestions(c);
            return
        }
        if (!this.active) {
            return
        }
        var d = this;
        if (this.cfg.itemtip) {
            this.itemtip.hide()
        }
        var b = {source: this.id, process: this.id, update: this.id, formId: this.cfg.formId, onsuccess: function(g, e, f) {
                PrimeFaces.ajax.Response.handle(g, e, f, {widget: d, handle: function(h) {
                        this.panel.html(h);
                        if (this.cfg.cache) {
                            this.cache[c] = h
                        }
                        this.showSuggestions(c)
                    }});
                return true
            }};
        b.params = [{name: this.id + "_query", value: c}];
        if (this.hasBehavior("query")) {
            var a = this.cfg.behaviors.query;
            a.call(this, b)
        } else {
            PrimeFaces.ajax.AjaxRequest(b)
        }
    }, show: function() {
        this.alignPanel();
        if (this.cfg.effect) {
            this.panel.show(this.cfg.effect, {}, this.cfg.effectDuration)
        } else {
            this.panel.show()
        }
    }, hide: function() {
        this.panel.hide();
        this.panel.css("height", "auto");
        if (this.cfg.itemtip) {
            this.itemtip.hide()
        }
    }, invokeItemSelectBehavior: function(b, d) {
        if (this.cfg.behaviors) {
            var c = this.cfg.behaviors.itemSelect;
            if (c) {
                var a = {params: [{name: this.id + "_itemSelect", value: d}]};
                c.call(this, a)
            }
        }
    }, invokeItemUnselectBehavior: function(c, d) {
        if (this.cfg.behaviors) {
            var a = this.cfg.behaviors.itemUnselect;
            if (a) {
                var b = {params: [{name: this.id + "_itemUnselect", value: d}]};
                a.call(this, b)
            }
        }
    }, removeItem: function(b, a) {
        var d = a.attr("data-token-value"), c = this;
        this.hinput.children("option").filter('[value="' + d + '"]').remove();
        a.fadeOut("fast", function() {
            var e = $(this);
            e.remove();
            c.invokeItemUnselectBehavior(b, d)
        })
    }, setupForceSelection: function() {
        this.currentItems = [this.input.val()];
        var a = this;
        this.input.blur(function() {
            var d = $(this).val(), c = false;
            for (var b = 0; b < a.currentItems.length; b++) {
                if (a.currentItems[b] === d) {
                    c = true;
                    break
                }
            }
            if (!c) {
                if (a.cfg.multiple) {
                    a.input.val("")
                } else {
                    a.input.val("");
                    a.hinput.val("")
                }
            }
        })
    }, disable: function() {
        this.disabled = true;
        this.input.addClass("ui-state-disabled").attr("disabled", "disabled")
    }, enable: function() {
        this.disabled = false;
        this.input.removeClass("ui-state-disabled").removeAttr("disabled")
    }, close: function() {
        this.hide()
    }, deactivate: function() {
        this.active = false
    }, activate: function() {
        this.active = true
    }, hasBehavior: function(a) {
        if (this.cfg.behaviors) {
            return this.cfg.behaviors[a] != undefined
        }
        return false
    }, alignPanel: function() {
        var c = null;
        if (this.cfg.multiple) {
            c = this.multiItemContainer.innerWidth() - (this.input.position().left - this.multiItemContainer.position().left)
        } else {
            if (this.panel.is(":visible")) {
                c = this.panel.children(".ui-autocomplete-items").outerWidth()
            } else {
                this.panel.css({visibility: "hidden", display: "block"});
                c = this.panel.children(".ui-autocomplete-items").outerWidth();
                this.panel.css({visibility: "visible", display: "none"})
            }
            var b = this.input.outerWidth();
            if (c < b) {
                c = b
            }
        }
        if (this.cfg.scrollHeight) {
            var a = this.panel.is(":hidden") ? this.panel.height() : this.panel.children().height();
            if (a > this.cfg.scrollHeight) {
                this.panel.height(this.cfg.scrollHeight)
            } else {
                this.panel.css("height", "auto")
            }
        }
        this.panel.css({left: "", top: "", width: c, "z-index": ++PrimeFaces.zindex});
        if (this.panel.parent().is(this.jq)) {
            this.panel.css({left: 0, top: this.jq.innerHeight()})
        } else {
            this.panel.position({my: "left top", at: "left bottom", of: this.input})
        }
    }});
