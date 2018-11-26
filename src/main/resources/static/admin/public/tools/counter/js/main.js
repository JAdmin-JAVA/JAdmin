// Standard Priority Calculator

var display1 = {
    operation: "",
    evaluation: "",
    answer: ""
};

// default flag values
var flag = {
    ansAllowed: false, // Initially do not allow the use of Ans button
    /*
    decimalPointAllowed: true,
    pctAllowed: false,
    ansAllowed: false,
    digitAllowed: true
    */
};

// default display values
$('#display1').val("");
$('#display2').val("");

// Set default theme (light)
$(".container").addClass("container-light");
$("form").addClass("form-light");
$("form input").addClass("form-input-light");
$(".operand-group").addClass("operand-group-light");
$(".operator-group").addClass("operator-group-light");
$("#equal").addClass("equal-light");
$("#clear").addClass("clear-light");
$("#backspace").addClass("backspace-light");

/*
try {
    myroutine(); // may throw three types of exceptions
} catch (e) {
    if (e instanceof TypeError) {
        // statements to handle TypeError exceptions
    } else if (e instanceof RangeError) {
        // statements to handle RangeError exceptions
    } else if (e instanceof EvalError) {
        // statements to handle EvalError exceptions
    } else {
       // statements to handle any unspecified exceptions
       logMyErrors(e); // pass exception object to error handler
    }
}
function isValidJSON(text) {
    try {
        JSON.parse(text);
        return true;
    } catch {
        return false;
    }
}
*/
function evaluate() {
    try {
        math.eval(display1.operation);
        display1.evaluation = math.eval(display1.operation);
        return true; // no exception occured
    } catch (e) {
        if (e instanceof SyntaxError) { // Syntax error exception
            display1.evaluation = "E";
            return false; // exception occured
        }
        else {// Unspecified exceptions
            display1.evaluation = "UE";
            return false; // exception occured
        }
    }
}

// Digits
$('#zero').on('click', function () {
    display1.operation = display1.operation + "0";
    $('#display1').val($('#display1').val() + '\u0030');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#one').on('click', function () {
    display1.operation = display1.operation + "1";
    $('#display1').val($('#display1').val() + '\u0031');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#two').on('click', function () {
    display1.operation = display1.operation + "2";
    $('#display1').val($('#display1').val() + '\u0032');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#three').on('click', function () {
    display1.operation = display1.operation + "3";
    $('#display1').val($('#display1').val() + '\u0033');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#four').on('click', function () {
    display1.operation = display1.operation + "4";
    $('#display1').val($('#display1').val() + '\u0034');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#five').on('click', function () {
    display1.operation = display1.operation + "5";
    $('#display1').val($('#display1').val() + '\u0035');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#six').on('click', function () {
    display1.operation = display1.operation + "6";
    $('#display1').val($('#display1').val() + '\u0036');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#seven').on('click', function () {
    display1.operation = display1.operation + "7";
    $('#display1').val($('#display1').val() + '\u0037');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#eight').on('click', function () {
    display1.operation = display1.operation + "8";
    $('#display1').val($('#display1').val() + '\u0038');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#nine').on('click', function () {
    display1.operation = display1.operation + "9";
    $('#display1').val($('#display1').val() + '\u0039');
    evaluate();
    $('#display2').val(display1.evaluation);
})
              
$('#decimal').on('click', function () {
    display1.operation = display1.operation + ".";
    $('#display1').val($('#display1').val() + '\u002e');
    evaluate();
    $('#display2').val(display1.evaluation);
})

// Operators
$('#left-parenthesis').on('click', function () {
    display1.operation = display1.operation + "(";
    $('#display1').val($('#display1').val() + '\u0028');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#right-parenthesis').on('click', function () {
    display1.operation = display1.operation + ")";
    $('#display1').val($('#display1').val() + '\u0029');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#add').on('click', function () {
    display1.operation = display1.operation + "+";
    $('#display1').val($('#display1').val() + '\u002b');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#subtract').on('click', function () {
    display1.operation = display1.operation + "-";
    $('#display1').val($('#display1').val() + '\u2212');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#multiply').on('click', function () {
    display1.operation = display1.operation + "*";
    $('#display1').val($('#display1').val() + '\u00d7');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#divide').on('click', function () {
    display1.operation = display1.operation + "/";
    $('#display1').val($('#display1').val() + '\u00f7');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#square-root').on('click', function () {
    
    var count = 0;
    var index = -1;
    var radicand = "";
    var result = "";
    
    // radicand is a complex expression
    if (display1.operation.endsWith(')')) 
    {
        // Find the position of matching parenthesis
        // For example if operation="2+(3-(3+4))", then the index of the 
        // matching '(' would be 2 and the radicand would be (3-(3+4))
        for (i = display1.operation.length - 2; i > -1; i--) 
        {
            if (display1.operation.charAt(i) === '(') 
            {
                if (count === 0) 
                {
                    index = i;
                    break;
                } 
                else 
                {
                    count -= 1;
                    continue;
                }
            } 
            else if (display1.operation.charAt(i) === ')') 
            {
                count += 1;
            } 
            else 
            {
                continue;
            }
        }
        if (index === -1) 
        {
            //alert("Malformed expression");
            $('#display2').val("Malformed expression");
            return;
        }
    } 
    // radicand is a single number
    else if (display1.operation.endsWith('0') || display1.operation.endsWith('1') || 
               display1.operation.endsWith('2') || display1.operation.endsWith('3') || 
               display1.operation.endsWith('4') || display1.operation.endsWith('5') ||
               display1.operation.endsWith('6') || display1.operation.endsWith('7') || 
               display1.operation.endsWith('8') || display1.operation.endsWith('9') ||
               display1.operation.endsWith('.'))
    {
        index = display1.operation.length - 1;
        for (i = display1.operation.length - 2; i > -1; i--) 
        {
            if (display1.operation.charAt(i) === '0' || display1.operation.charAt(i) === '1' ||
                display1.operation.charAt(i) === '2' || display1.operation.charAt(i) === '3' ||
                display1.operation.charAt(i) === '4' || display1.operation.charAt(i) === '5' ||
                display1.operation.charAt(i) === '6' || display1.operation.charAt(i) === '7' ||
                display1.operation.charAt(i) === '8' || display1.operation.charAt(i) === '9' ||
                display1.operation.charAt(i) === '.' || display1.operation.charAt(i) === '^')
            {
                index = i;
            }
            else 
            {
                break;
            }
        }
    }
    else 
    {
        return;
    }
    
    // Get the radicand
    radicand = display1.operation.substring(index, display1.operation.length);
    
    // Update operation with sqrt(radicand)
    display1.operation = display1.operation.substring(0, index) + "sqrt(" + radicand + ")";
    
    // Replace '^2' with superscript two (don't forget to escape '^' with '\')
    radicand = radicand.replace(/\^2/g, "\u00b2");
    
    // Find the last occurence of radicand
    index = $('#display1').val().lastIndexOf(radicand);
    
    radicand = $('#display1').val().substring(index, $('#display1').val().length);
    
    // Update #display1
    $('#display1').val($('#display1').val().substring(0, index) + '\u221a' + radicand);
    
    // Evaluate
    evaluate();
    
    // Display current evaluation
    $('#display2').val(display1.evaluation);
});
        
$('#square').on('click', function () {
    display1.operation = display1.operation + "^2";
    $('#display1').val($('#display1').val() + '\u00b2');
    evaluate();
    $('#display2').val(display1.evaluation);
})

$('#percentage').on('click', function () {
    /*
    // Only one % is allowed for the entire operation.
    // The last % indicates the end of the entire operation and triggers the equal button.
    if (display1.numOfPct < 1 && flag.pctAllowed) {
        //Num
        if (display1.numOfOperands === 0) {
            display1.operand = eval(display1.operand + "/" + "100").toString();
            $('#display1').val(display1.operand);
            $('#display2').val(display1.operand);
        }
        else {
            $('#display1').val($('#display1').val() + '\u0025');
            display1.numOfPct ++;
            switch (display1.operator) {
                // a*b%
                case "*":
                    // a*b/100
                    display1.operand = eval(display1.evaluation + "*" + display1.operand + "/" + "100").toString();
                    display1.operation = "1";
                    // result = 1 * (a*b/100)
                    $('#display2').val(eval(display1.operation + display1.operator + display1.operand).toString());
                    if (display1.numOfOperands)
                    break;

                // a/b%
                case "/":
                    // a/b*100
                    display1.operand = eval(display1.evaluation + "/" + display1.operand + "*" + "100").toString();
                    display1.operation = "1";
                    display1.operator = "*";
                    // result = 1 * (a/b*100)
                    $('#display2').val(eval(display1.operation + display1.operator + display1.operand).toString());
                    break;

                // a+b%, a-b%
                default:
                    // a*b/100
                    display1.operand = eval(display1.evaluation + "*" + display1.operand + "/" + "100").toString();
                    // result = a + (a*b/100), result = a - (a*b/100)
                    $('#display2').val(eval(display1.operation + display1.operator + display1.operand).toString());
                    break;
            }
            // Execute the handler attached to the $("#equal") element for the 'click' event type.
            $("#equal").trigger("click");
        }
    }
    */
})

// Clear
$('#clear').on('click', function () {
    display1.operation = "",
    display1.evaluation = "",
    $('#display1').val("");
    $('#display2').val("");
})

// Equal
$('#equal').on('click', function () {
    display1.answer = display1.evaluation; // Store the answer (Ans button)
    $('#display1').val(display1.answer); // Update display1
    $('#display2').val(""); // Update display2
    display1.operation = display1.answer; // Current operation equals the answer
    flag.ansAllowed = true; // Allow the use of Ans button
})

$('#ans').on('click', function () {    
    
    // Allow 'Ans' when its flag is enabled
    if (flag.ansAllowed) {
        if (flag.squareRoot) {
            display1.operation = display1.operation.substring(0, display1.operation.length-1) + display1.answer + ")";
        }
        else {
            display1.operation = display1.operation + display1.answer;
        }
        $('#display1').val($('#display1').val() + 'Ans');
        evaluate();
        $('#display2').val(display1.evaluation);
    }
})

// Backspace
$('#backspace').on('click', function () {    
    display1.operation = display1.operation.slice(0, display1.operation.length-1);
    $('#display1').val($('#display1').val().slice(0, $('#display1').val().length-1));
    evaluate();
    $('#display2').val(display1.evaluation);
})

// Theme system
$("input[type='checkbox']").change(function () {
    // dark theme
    if (this.checked) {
        //alert("dark");
        $(".container").removeClass("container-light");
        $(".container").addClass("container-dark");
        $("form").removeClass("form-light");
        $("form").addClass("form-dark");
        $("form input").removeClass("form-input-light");
        $("form input").addClass("form-input-dark");
        $(".operand-group").removeClass("operand-group-light");
        $(".operand-group").addClass("operand-group-dark");
        $(".operator-group").removeClass("operator-group-light");
        $(".operator-group").addClass("operator-group-dark");
        $("#equal").removeClass("equal-light");
        $("#equal").addClass("equal-dark");
        $("#clear").removeClass("clear-light");
        $("#clear").addClass("clear-dark");
        $("#backspace").removeClass("backspace-light");
        $("#backspace").addClass("backspace-dark");
    }
    // light theme (default)
    else {
        //alert("light");
        $(".container").removeClass("container-dark");
        $(".container").addClass("container-light");
        $("form").removeClass("form-dark");
        $("form").addClass("form-light");
        $("form input").removeClass("form-input-dark");
        $("form input").addClass("form-input-light");
        $(".operand-group").removeClass("operand-group-dark");
        $(".operand-group").addClass("operand-group-light");
        $(".operator-group").removeClass("operator-group-dark");
        $(".operator-group").addClass("operator-group-light");
        $("#equal").removeClass("equal-dark");
        $("#equal").addClass("equal-light");
        $("#clear").removeClass("clear-dark");
        $("#clear").addClass("clear-light");
        $("#backspace").removeClass("backspace-dark");
        $("#backspace").addClass("backspace-light");
    }
})