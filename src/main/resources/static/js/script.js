const appUrl = window.location.protocol + "//" + window.location.host + "/";

window.onload = function() {
    $('#replay-hidden').hide();
};

$(document).ready( function () {

    let frameCounter = 0;
    let scorePosition = 0;
    let strike = false;
    let spare = false;
    let previousValue = 0;

    let array = [];

    $('#replay').on('click', () => {
       window.location.reload();
    });

    $('#score-buttons button').on('click', (e) => {
        strike = false;
        spare = false;

        if (e.target.innerHTML === "10") {
            if ((scorePosition % 2) === 0 || frameCounter >= 9) {
                updateRollDisplay(scorePosition, frameCounter, "X");
                strike = true;
            }

            scorePosition++;
            if (frameCounter < 9) {
                scorePosition = 0;
                frameCounter++;
            }

        }  else {

            console.log("Parsed value " + this.previousValue);

            if (scorePosition === 1 && this.previousValue + Number.parseInt(e.target.innerHTML) === 10) {
                updateRollDisplay(scorePosition, frameCounter, "/");
                spare = true;
            } else {
                updateRollDisplay(scorePosition, frameCounter, e.target.innerHTML);
            }

            scorePosition++;
            if(scorePosition === 2) {
                scorePosition = 0;
                frameCounter++;
            }
        }

        if (strike) {
            array.push("X");
        } else if (spare) {
            array.push("/");
        } else {
            array.push(e.target.innerHTML);
        }

        if (frameCounter === 9 && e.target.innerHTML !== "10" && this.previousValue + Number.parseInt(e.target.innerHTML) !== 10) {
            computeFinalScore(array);
        } else if (frameCounter === 9 && scorePosition === 3 || array.length === 21) {
            computeFinalScore(array);
        }
        // console.log("score position " + scorePosition);
        // console.log("frame counter " + frameCounter);
        this.previousValue = parseInt(e.target.innerHTML);
    });

});

function computeFinalScore(scores) {

    console.log("In method with length = " + scores.length);

    $.ajax({

        type: "POST",
        url: appUrl + "/computeScore",
        data: "scores=" + scores,
        success: function (output) {
            console.log("success" + output);
            $('#result').html(output);
            $('#replay-hidden').show();
        },
        error: function (result) {
            console.log("error" + result);

        }

    });

}

function updateRollDisplay(position, frame, content) {
    $('#score-table-display tr:eq(1) td:eq(' + ((frame * 2) + position) + ')').html(content);
}
