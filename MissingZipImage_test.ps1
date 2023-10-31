

[Reflection.Assembly]::LoadWithPartialName('System.IO.Compression.FileSystem') | Out-Null
$sourceFile = "{path}"
$sourceFile -replace ' ', '` '
$files = @([IO.Compression.ZipFile]::OpenRead($sourceFile).Entries.FullName )
$targetFound = 0
foreach($file in $files){
    $fileName = $file.Substring(0,($file.Length-4))
    $fileName = $fileName.Replace("%", "")
    Write-Host $fileName
    if($target -eq $fileName){
    $targetFound = 1
    #break
    }
}
Write-Host $targetFound
