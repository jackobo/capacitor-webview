@echo off
REM usage: export-commits.bat <start-date> <end-date> <branch-name>
REM example: export-commits.bat 2024-01-01 2024-02-01 main
echo Commit,Committer,CommitDate,Message > "I:\My Drive\Acte personale\PFA2\Facturi\Aeroitalia\GitCommits\CapacitorWebview_%3_%1_%2.csv"
git log --pretty=format:"%%h,%%an,%%ad,%%s" --date=format:"%%Y-%%m-%%d %%H:%%M:%%S" --after=%1 --before=%2 --reverse %3 >> "I:\My Drive\Acte personale\PFA2\Facturi\Aeroitalia\GitCommits\CapacitorWebview_%3_%1_%2.csv"
