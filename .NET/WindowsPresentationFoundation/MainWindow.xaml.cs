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
