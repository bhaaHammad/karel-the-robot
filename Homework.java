import stanford.karel.*;
public class Homework extends SuperKarel {
    int numberOfSteps = 0,horizontalSteps = 0,verticalSteps = 0;
    boolean flag = true;
    private void moving() {
        move();
        numberOfSteps++;
    }
    private void checkAndPutBeeper() {
        if(noBeepersPresent())
            putBeeper();
    }
    private void beepersAtAllPointsOfTheLine() {
        while(frontIsClear()) {
            checkAndPutBeeper();
            moving();
        }
        checkAndPutBeeper();
    }
    private void movingAtAllPointsOfTheLine() {
        while(frontIsClear())
            moving();
    }
    private void turnAroundInTheLeftLine() {
        turnLeft();
        moving();
        turnLeft();
    }
    private void turnAroundInTheRightLine() {
        turnRight();
        moving();
        turnRight();
    }
    public void lengthOfHorizontalLine() {
        while(frontIsClear()) {
            moving();
            horizontalSteps++;
        }
        backToMiddleHorizontalLine();
    }
    public void backToMiddleHorizontalLine() {
        turnAround();
        for(int i = 0;i< horizontalSteps/2;++i)
            moving();
        putBeeperVertically();
    }
    private void putBeeperVertically() {
        turnRight();
        while (frontIsClear()) {
            if (horizontalSteps > 1)
                checkAndPutBeeper();
            moving();
            verticalSteps++;
        }
        if (horizontalSteps > 1 && verticalSteps > 1)
            checkAndPutBeeper();
        whereShouldIGo();
    }
    private void whereShouldIGo() {
        if(verticalSteps <= 1 || horizontalSteps <= 1)
            specialCase();
        else if(horizontalSteps % 2 == 0)
            goToMiddleVerticalLine();
        else {
            putBeeperToUpperHalfVerticalLine();
            putBeeperToLowerHalfVerticalLine();
        }
    }
    private void goToMiddleVerticalLine() {
        if(verticalSteps % 2 == 0)
            goToMiddleVerticalOddLine();
        else
            goToMiddleVerticalEvenLine();
        putBeeperHorizontally();
    }
    private void goToMiddleVerticalOddLine() {
        turnRight();
        movingAtAllPointsOfTheLine();
        turnRight();
        for(int i = 0;i < verticalSteps/2;++i)
            moving();
    }
    private void goToMiddleVerticalEvenLine() {
        turnAround();
        for(int i = 0;i < verticalSteps/2;++i)
            moving();
    }
    private void putBeeperToUpperHalfVerticalLine() {
        turnAroundInTheLeftLine();
        for(int i = 0;i < verticalSteps/2;++i) {
            putBeeper();
            moving();
        }
        putBeeperHorizontally();
    }
    private void putBeeperToLowerHalfVerticalLine() {
        moving();
        turnLeft();
        while(frontIsClear()) {
            moving();
            checkAndPutBeeper();
        }
        KarelFinishedCutting();
    }
    private void putBeeperHorizontally() {
        if(horizontalSteps % 2 == 0) {
            if(verticalSteps % 2 == 0)
                putBeeperHorizontallyInOddLines();
            else
                putBeeperHorizontallyInOddHorizontalEvenVerticalLine();
        }
        else {
            turnRight();
            beepersAtAllPointsOfTheLine();
            if(verticalSteps % 2 == 0)
                putBeeperHorizontallyInEvenHorizontalAndOddVerticalLines();
            else
                putBeeperHorizontallyInEvenHorizontalAndEvenVerticalLines();
        }
    }
    private  void putBeeperHorizontallyInEvenHorizontalAndEvenVerticalLines() {
        turnAroundInTheLeftLine();
        beepersAtAllPointsOfTheLine();
        turnAroundInTheLeftLine();
        while(noBeepersPresent()) {
            putBeeper();
            moving();
        }
    }
    private  void putBeeperHorizontallyInEvenHorizontalAndOddVerticalLines() {
        turnAround();
        beepersAtAllPointsOfTheLine();
        turnAround();
        for(int i = 0;i < horizontalSteps/2;++i)
            moving();
    }
    private void putBeeperHorizontallyInOddLines(){
        turnRight();
        beepersAtAllPointsOfTheLine();
        KarelFinishedCutting();
    }
    private void putBeeperHorizontallyInOddHorizontalEvenVerticalLine() {
        turnLeft();
        beepersAtAllPointsOfTheLine();
        turnAroundInTheRightLine();
        beepersAtAllPointsOfTheLine();
        turnAroundInTheRightLine();
        for(int i =0 ;i < horizontalSteps/2-1;++i) {
            checkAndPutBeeper();
            moving();
        }
        checkAndPutBeeper();
        KarelFinishedCutting();
    }
    private void specialCase() {
        if(verticalSteps == 1) {
            turnAround();
            moving();
            if(beepersPresent())
                pickBeeper();
            turnAround();
        }
        if((verticalSteps <= 1 && horizontalSteps >= 7 ) || (verticalSteps >= 7 && horizontalSteps <= 1))
            putBeeperInSpecialCaseIfLengthGreaterThanSeven();
        else if((horizontalSteps < 7 && horizontalSteps > 1 && verticalSteps <= 1 ) ||
                (horizontalSteps <= 1 && verticalSteps <7 && verticalSteps > 1))
            putBeeperInSpecialCaseIfLengthGreaterThanTwoAndLessThanEight();
        KarelFinishedCutting();
    }

    private void putBeeperInSpecialCaseIfLengthGreaterThanSeven() {
        incrementVerticalAndHorizontalStepsByOne();
        int k = horizontalSteps * verticalSteps;
        int steps = k / 4;
        int neglectedArea = k % 4;
        if (verticalSteps <= 2)
            goToBeginningOfTheHorizontalLineInSpecialCase();
        turnAround();
        if (neglectedArea == 3)
            divideWorldUsingThreeBeepers(steps);
        else
            divideWorldUsingAppropriateBeepers(steps,neglectedArea);
    }
    private void goToBeginningOfTheHorizontalLineInSpecialCase() {
        turnRight();
        movingAtAllPointsOfTheLine();
    }
    private void incrementVerticalAndHorizontalStepsByOne() {
        if (verticalSteps != 1 && horizontalSteps != 1) {
            verticalSteps++;
            horizontalSteps++;
        } else {
            flag=false;
            if (verticalSteps != 1)
                verticalSteps++;
            else
                horizontalSteps++;
        }
    }
    private void divideWorldUsingAppropriateBeepers(int steps,int neglectedArea) {
        for (int i = 0; i < neglectedArea; ++i) {
            putBeeper();
            if (flag == false)
                putBeeperOnTheOppositeSide();
            moving();
        }
        while (frontIsClear()) {
            putBeeper();
            if (flag == false)
                putBeeperOnTheOppositeSide();
            for (int i = 0; i < steps; ++i) {
                if (frontIsClear())
                    moving();
            }
        }
    }
    private void divideWorldUsingThreeBeepers(int steps) {
        for(int i = 0;i < steps;++i)
            moving();
        if (flag == false)
            putBeeperOnTheOppositeSide();
        putBeeper();
        for(int i = 0;i < 2;++i) {
            for(int y=0;y<=steps;++y)
                moving();
            if (flag == false)
                putBeeperOnTheOppositeSide();
            putBeeper();
        }
        for(int i=0;i<steps;++i)
            moving();
    }
    private void putBeeperInSpecialCaseIfLengthGreaterThanTwoAndLessThanEight() {
        if (verticalSteps <= 1) {
            turnRight();
            movingAtAllPointsOfTheLine();
        }
        turnAround();
        while(frontIsClear()) {
            moving();
            putBeeper();
            if(verticalSteps == 1 || horizontalSteps == 1)
                putBeeperOnTheOppositeSide();
            if(frontIsClear())
                moving();
        }
    }
    private void putBeeperOnTheOppositeSide() {
        turnRight();
        moving();
        putBeeper();
        turnAround();
        moving();
        turnRight();
    }
    private void KarelFinishedCutting() {
        while(notFacingWest())
            turnLeft();
        while(frontIsClear())
            moving();
        turnLeft();
        while(frontIsClear())
            moving();
        turnLeft();
    }
    public void run() {
        lengthOfHorizontalLine();
        System.out.println(numberOfSteps);
    }
}

