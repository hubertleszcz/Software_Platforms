using System;
using System.Collections.Generic;
using System.IO;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Forms;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;
using static System.Windows.Forms.VisualStyles.VisualStyleElement.Button;

namespace WindowsPresentationFoundation
{
    /// <summary>
    /// Interaction logic for MainWindow.xaml
    /// </summary>
    public partial class MainWindow : Window
    {
        private void Exit_Click(object sender, RoutedEventArgs e)
        {
            Close();
        }

        private void addToTreeView(DirectoryInfo directoryInfo, TreeViewItem parentNode)
        {
            foreach (var directory in directoryInfo.GetDirectories())
            {
                var item = new TreeViewItem
                {
                    Header = directory.Name,
                    Tag = directory.FullName
                };
                var contextMenu = new ContextMenu();
                var delete = new MenuItem { Header = "Delete" };
                delete.Click += (sender, args) =>
                {
                    if (Directory.Exists(item.Tag.ToString()))
                    {
                        var dirInfo = new DirectoryInfo(item.Tag.ToString());
                        if ((dirInfo.Attributes & FileAttributes.ReadOnly) != 0)
                        {
                            dirInfo.Attributes &= ~FileAttributes.ReadOnly;
                        }
                        dirInfo.Delete(true);
                        parentNode.Items.Remove(item);
                    }
                };
                contextMenu.Items.Add(delete);
                var add = new MenuItem { Header = "Add" };
                add.Click += (sender, args) =>
                {
                    var form = new Form { Width = 350, Height = 500 };
                    var fileName = new System.Windows.Forms.TextBox { Location = new System.Drawing.Point(10, 10), Width = 250 };
                    form.Controls.Add(fileName);
                    var groupBox = new System.Windows.Forms.GroupBox { Location = new System.Drawing.Point(15, 45), Width = 200, Height = 100 };
                    var radioButtonFile = new System.Windows.Forms.RadioButton { Text = "File", Location = new System.Drawing.Point(10, 20) };
                    var radioButtonFolder = new System.Windows.Forms.RadioButton { Text = "Folder", Location = new System.Drawing.Point(10, 45) };
                    groupBox.Controls.Add(radioButtonFile);
                    groupBox.Controls.Add(radioButtonFolder);
                    form.Controls.Add(groupBox);
                    var rahsBox = new System.Windows.Forms.GroupBox { Location = new System.Drawing.Point(15, 155), Width = 200, Height = 200 };
                    var readOnlyBox = new System.Windows.Forms.CheckBox { Text = "ReadOnly", Location = new System.Drawing.Point(10, 20) };
                    var archiveBox = new System.Windows.Forms.CheckBox { Text = "Archive", Location = new System.Drawing.Point(10, 45) };
                    var hiddenBox = new System.Windows.Forms.CheckBox { Text = "Hidden", Location = new System.Drawing.Point(10, 70) };
                    var systemBox = new System.Windows.Forms.CheckBox { Text = "System", Location = new System.Drawing.Point(10, 95) };
                    rahsBox.Controls.Add(readOnlyBox);
                    rahsBox.Controls.Add(archiveBox);
                    rahsBox.Controls.Add(hiddenBox);
                    rahsBox.Controls.Add(systemBox);
                    form.Controls.Add(rahsBox);

                    var okButton = new System.Windows.Forms.Button { Text = "OK", Location = new System.Drawing.Point(10, 350), Width = 100 };
                    okButton.Click += (sender, args) =>
                    {
                    };
                    form.Controls.Add(okButton);

                    var cancelButton = new System.Windows.Forms.Button { Text = "Cancel", Location = new System.Drawing.Point(120, 350), Width = 100 };
                    cancelButton.Click += (sender, args) =>
                    {
                        form.Close();
                    };
                    form.Controls.Add(cancelButton);

                    form.ShowDialog();
                };

                contextMenu.Items.Add(add);
                item.ContextMenu = contextMenu;
                parentNode.Items.Add(item);
                addToTreeView(directory, item);
            }

            foreach (var file in directoryInfo.GetFiles())
            {
                var item = new TreeViewItem
                {
                    Header = file.Name,
                    Tag = file.FullName
                };

                var contextMenu = new ContextMenu();
                var delete = new MenuItem { Header = "Delete" };
                delete.Click += (sender, args) =>
                {
                    if (File.Exists(item.Tag.ToString()))
                    {
                        var fileInfo = new FileInfo(item.Tag.ToString());
                        if (fileInfo.IsReadOnly)
                        { 
                            fileInfo.IsReadOnly = false;
                        }
                        fileInfo.Delete();
                        parentNode.Items.Remove(item);
                    }
                };
                contextMenu.Items.Add(delete);
                var openFile = new MenuItem { Header = "Open" };
                openFile.Click += (sender, args) =>
                {
                    var reader = file.OpenText();
                    fileContent.Text = reader.ReadToEnd();
                };
                contextMenu.Items.Add(openFile);
                item.ContextMenu = contextMenu;

                parentNode.Items.Add(item);
            }
        }

        private void Open_Click(object sender, RoutedEventArgs e)
        {
            var dlg = new FolderBrowserDialog() { Description = "Select directory to open" };
            dlg.ShowDialog();
            treeView.Items.Clear();
            var dir = new DirectoryInfo(dlg.SelectedPath);
            var root = new TreeViewItem
            {
                Header = dir.Name,
                Tag = dir.FullName
            };
            treeView.Items.Add(root);
            addToTreeView(dir, root);
        }
        public MainWindow()
        {
            InitializeComponent();
        }
    }
}
