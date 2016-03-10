module.exports = function() {
    //default place holder
    return {
        restrict: 'A',
        link: function(scope, element, attr) {
            var img = 'assets/images/placeholder.png';
            //If ngSrc is empty
            if (!attr.ngSrc)
                element[0].src = img;

            //If there is an error (404)
            element.on('error', function() {
                element[0].src = img;
            });

        }
    };
}
