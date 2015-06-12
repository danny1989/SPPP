function hide(idModal) {
    $(idModal).modal('hide');
    if ($(".modal-backdrop") !== null) {
        $(".modal-backdrop").remove();
    }
}
function show(idModal) {
    $(idModal).modal('show');

}
function showMessage(sumary, detail, tipe) {
    PF('growl').renderMessage({"summary": sumary,
        "detail": detail,
        "severity": tipe});
    //"warn"
    //"info
    //error
}
function reiniciarInputs(idContenido) {
    $('#' + idContenido).find('form').find('input[type=text], textarea').val('');
}
function limpiarAutoComplete() {
    $('.ui-autocomplete .ui-autocomplete-input').addClass('form-control');
    $('.ui-autocomplete').removeClass();
}
function limpiarCSSTablaPrimeFaces() {
    console.log("Limipar");
    $('table').addClass('table table-bordered table-striped table-condensed table-hover smart-form has-tickbox');

    $('table').closest('div.ui-datatable').removeClass();
    $('table thead th').removeClass('ui-state-default');
}

function changeInput(event, id) {
    console.log(event);
    if (event.keyCode == 13) {
        document.getElementById(id).focus();
        return false;
    }
}

function clickButton(event, id) {
    console.log(id);
    if (event.keyCode == 13) {
        document.getElementById(id).click();
        return false;
    }
}

function sumarUnaColumnaTabla(idTabla, columna) {

    var tabla = document.getElementById(idTabla);
    var fila = tabla.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    var total = 0;
    for (i = 0; i < fila.length; i++) {
        var celda = fila[i].getElementsByTagName("td")[columna];
        var texto = celda.getElementsByTagName("input");
        var num = parseFloat(texto[0].value);
        if (!isNaN(num)) {
            total = total + num;
        }
    }

    return total;
}

function sumarUnaColumnaTablaLabel(idTabla, columna) {
    var tabla = document.getElementById(idTabla);
    var fila = tabla.getElementsByTagName("tbody")[0].getElementsByTagName("tr");
    var total = 0;
    for (i = 0; i < fila.length; i++) {
        var celda = fila[i].getElementsByTagName("td")[columna];
        var texto = celda.getElementsByTagName("label");
        var num = parseFloat(texto[0].value);
        if (!isNaN(num)) {
            total = total + num;
        }
    }
    return total;
}