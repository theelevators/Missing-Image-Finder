param([String]$sourceFile) 

[Reflection.Assembly]::LoadWithPartialName('System.IO.Compression.FileSystem') | Out-Null
$sourceFile -replace 'space', ' '
$sourceFile -replace 'tick', "'"
$sourceFile -replace ' ', '` '
write-host $out
$files = @([IO.Compression.ZipFile]::OpenRead($sourceFile).Entries.FullName )
$out = ""
foreach($file in $files){
    $fileName = $file.Substring(0,($file.Length-4))
    $fileName = $fileName.Replace("%", "")

    if ($out -eq ""){
    $out = $fileName
    }else{
    $out = $out + "," + $fileName
    }
     
}
Write-Host $out
