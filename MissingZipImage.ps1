param([String]$target, [String]$sourceFile) 

[Reflection.Assembly]::LoadWithPartialName('System.IO.Compression.FileSystem') | Out-Null

$sourceFile -replace ' ', '` '
$files = @([IO.Compression.ZipFile]::OpenRead($sourceFile).Entries.FullName )
$targetFound = 0
foreach($file in $files){
    $fileName = $file.Substring(0,($file.Length-4))
    $fileName = $fileName.Replace("%", "")
    if($target -eq $fileName){
    $targetFound = 1
    break
    }
}
Write-Host $targetFound
