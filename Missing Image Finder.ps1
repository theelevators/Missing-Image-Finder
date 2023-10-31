Add-Type -AssemblyName System.Windows.Forms
Add-Type -AssemblyName System.Drawing
 
################## PATH BOX ################## 
$form = New-Object System.Windows.Forms.Form
$form.Text = 'Missing Image Report Finder'
$form.Size = New-Object System.Drawing.Size(350, 200)
$form.StartPosition = 'CenterScreen'

$okButton = New-Object System.Windows.Forms.Button
$okButton.Location = New-Object System.Drawing.Point(75, 130)
$okButton.Size = New-Object System.Drawing.Size(75, 23)
$okButton.Text = 'OK'
$okButton.DialogResult = [System.Windows.Forms.DialogResult]::OK
$form.AcceptButton = $okButton
$form.Controls.Add($okButton)

$cancelButton = New-Object System.Windows.Forms.Button
$cancelButton.Location = New-Object System.Drawing.Point(180, 130)
$cancelButton.Size = New-Object System.Drawing.Size(75, 23)
$cancelButton.Text = 'Cancel'
$cancelButton.DialogResult = [System.Windows.Forms.DialogResult]::Cancel
$form.CancelButton = $cancelButton
$form.Controls.Add($cancelButton)

$label = New-Object System.Windows.Forms.Label
$label.Location = New-Object System.Drawing.Point(10, 20)
$label.Size = New-Object System.Drawing.Size(330, 20)
$label.Text = 'Enter path for the Missing Image Report:'
$form.Controls.Add($label)

$textBox = New-Object System.Windows.Forms.TextBox
$textBox.Location = New-Object System.Drawing.Point(10, 40)
$textBox.Size = New-Object System.Drawing.Size(300, 20)
$form.Controls.Add($textBox)

$label2 = New-Object System.Windows.Forms.Label
$label2.Location = New-Object System.Drawing.Point(10, 70)
$label2.Size = New-Object System.Drawing.Size(330, 20)
$label2.Text = 'Enter path to save new file:'
$form.Controls.Add($label2)

$textBox2 = New-Object System.Windows.Forms.TextBox
$textBox2.Location = New-Object System.Drawing.Point(10, 90)
$textBox2.Size = New-Object System.Drawing.Size(300, 20)
$form.Controls.Add($textBox2)

$form.Topmost = $true

$form.Add_Shown({ $textBox.Select() })
$form.Add_Shown({ $textBox2.Select() })

$result = $form.ShowDialog()

################## Start Process ################## 

if ($result -eq [System.Windows.Forms.DialogResult]::OK) {
    $originalPath = $textBox.Text
    $output = $textBox2.Text
    Write-host "Starting Process...."

    cd "{PATH}" 
    java -jar  "MissingImageFinder.jar"  $originalPath $output


    } 
 
Read-Host -Prompt "Press Enter To Exit"