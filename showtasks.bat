if "%ERRORLEVEL%" == "0" goto runwebsite
echo.
echo showtasks.bat has errors – breaking work
goto fail

:runwebsite
start firefox --new-window http://localhost:8080/crud/v1/task/tasks
goto end

:fail
echo.
echo There were errors!

:end
echo.
echo Firefox started.