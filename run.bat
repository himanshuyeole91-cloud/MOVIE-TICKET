@echo off
echo Compiling Movie Ticket Booking System...

if not exist "target\classes" mkdir target\classes

javac -d target/classes -sourcepath src/main/java src/main/java/com/movieticket/model/*.java src/main/java/com/movieticket/service/*.java src/main/java/com/movieticket/*.java

if %errorlevel% neq 0 (
    echo Compilation failed!
    pause
    exit /b 1
)

echo Compilation successful!
echo.
echo Running Movie Ticket Booking System...
echo.
java -cp target/classes com.movieticket.MovieTicketBookingSystem

pause

