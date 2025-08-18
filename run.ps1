# run.ps1 - Script para compilar e executar o projeto oficina-playground

Write-Host "==> Limpando saída antiga..."
if (Test-Path out) { Remove-Item -Recurse -Force out }
New-Item -ItemType Directory -Force -Path out | Out-Null

Write-Host "==> Compilando código..."
$files = Get-ChildItem -Recurse -Filter *.java src\main\java | ForEach-Object { $_.FullName }
javac -d out $files

if ($LASTEXITCODE -ne 0) {
    Write-Host "❌ Erro na compilação" -ForegroundColor Red
    exit $LASTEXITCODE
}

Write-Host "==> Executando programa..."
java -cp out br.com.oficina.Main
