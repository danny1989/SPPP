/**
 * funcion para asignar la calioficacion mostrada en la vista
 * validado que tenga una nota entre 0 - 20 .
 * @param {event} e la variable de evento
 * @param {double} value es el valos de la nota
 * @param {int} id id de la tabla curso_operacion_alumno_evaluacion
 * @returns {Boolean}
 */
function precalificar(e, value, id) {
    if (e.keyCode === 13 && value.length !== 0) {
        if (value <= 20) {
            asignarNota([{name: 'idCOAE', value: id}, {name: 'nota', value: value}]);
        } else {
            value = "";
            alert('NOTA NO VÁLIDA, EL RANGO DEBE DE ESTAR ENTRE 0 - 20');
        }
        return false;
    }
}

