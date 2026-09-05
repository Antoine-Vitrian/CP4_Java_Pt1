$ErrorActionPreference = 'Stop'

$root = Split-Path -Parent $MyInvocation.MyCommand.Path
$tempClasses = Join-Path $env:TEMP 'cp4-java-classes'
if (Test-Path $tempClasses) {
    Remove-Item -Recurse -Force $tempClasses
}
New-Item -ItemType Directory -Path $tempClasses | Out-Null

$sourceFiles = @(
    Get-ChildItem -Path (Join-Path $root 'src/main/java') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
    Get-ChildItem -Path (Join-Path $root 'src/test/java') -Recurse -Filter *.java | ForEach-Object { $_.FullName }
)

& javac -d $tempClasses $sourceFiles
if ($LASTEXITCODE -ne 0) { exit $LASTEXITCODE }

& java -cp $tempClasses com.fiap.dao.ProdutoDAOImplTest
exit $LASTEXITCODE
