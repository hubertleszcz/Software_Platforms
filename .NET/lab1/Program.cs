using System;
using System.IO;
using System.Runtime.Serialization.Formatters.Binary;

[Serializable]
class StringLengthComparer : IComparer<string>
{
    public int Compare(string x, string y)
    {
        int lengthComparison = x.Length.CompareTo(y.Length);
        if (lengthComparison != 0)
        {
            return lengthComparison;
        }
        else
        {
            return string.Compare(x, y, StringComparison.Ordinal);
        }
    }
}


public static class DirectoryInfoExtensions
{
    public static DateTime FindOldestDate(this DirectoryInfo directory)
    {
        DateTime oldestDate = DateTime.MaxValue;

        // Przeglądanie plików w bieżącym katalogu
        foreach (var file in directory.GetFiles())
        {
            if (file.LastWriteTime < oldestDate)
            {
                oldestDate = file.LastWriteTime;
            }
        }

        // Przeglądanie podkatalogów
        foreach (var subDirectory in directory.GetDirectories())
        {
            DateTime subOldestDate = FindOldestDate(subDirectory); // Rekurencyjne wywołanie dla podkatalogu
            if (subOldestDate < oldestDate)
            {
                oldestDate = subOldestDate;
            }
        }

        return oldestDate;
    }
}


public static class FileSystemInfoExtensions
{
    public static string GetDosAttributes(this FileSystemInfo fileInfo)
    {
        string dosAttributes = string.Empty;

        if ((fileInfo.Attributes & FileAttributes.ReadOnly) != 0)
            dosAttributes += "r";
        else
            dosAttributes += "-";

        if ((fileInfo.Attributes & FileAttributes.Archive) != 0)
            dosAttributes += "a";
        else
            dosAttributes += "-";

        if ((fileInfo.Attributes & FileAttributes.Hidden) != 0)
            dosAttributes += "h";
        else
            dosAttributes += "-";

        if ((fileInfo.Attributes & FileAttributes.System) != 0)
            dosAttributes += "s";
        else
            dosAttributes += "-";

        return dosAttributes;
    }
}

static class lab1
{
    static void printOut(string path, int tabs)
    {
        string[] files = Directory.GetFiles(path);
        string[] dirs = Directory.GetDirectories(path);
        foreach (string file in files)
        {
            FileInfo info = new FileInfo(file);
            Console.WriteLine(new string('-', tabs) + Path.GetFileName(file) + $" {info.Length} bajtów {info.GetDosAttributes()}");
        }
        foreach (string dir in dirs)
        {
            Console.WriteLine(new string('-', tabs) + Path.GetFileName(dir) + $"({Directory.GetFiles(dir).Length + Directory.GetDirectories(dir).Length}) ");
            printOut(dir, tabs + 1);
        }
    }
    static SortedDictionary<string, long> LoadElements(string path)
    {
        SortedDictionary<string, long> elements = new SortedDictionary<string, long>(new StringLengthComparer());
        string[] files = Directory.GetFiles(path);
        string[] dirs = Directory.GetDirectories(path);

        foreach (string file in files)
        {
            FileInfo fileInfo = new FileInfo(file);
            elements.Add(fileInfo.Name, fileInfo.Length);
        }

        foreach (string dir in dirs)
        {
            DirectoryInfo dirInfo = new DirectoryInfo(dir);
            elements.Add(dirInfo.Name, Directory.GetFileSystemEntries(dir).Length);
        }

        BinaryFormatter formatter = new BinaryFormatter();

        return elements;
    }

    static void DisplayCollection(SortedDictionary<string, long> collection)
    {
        Console.WriteLine();
        foreach (var pair in collection)
        {
            Console.WriteLine($"{pair.Key} -> {pair.Value}");
        }
        Console.WriteLine();
    }

    static void SerializeCollection(SortedDictionary<string, long> collection, string fileName)
    {
        using (FileStream fs = new FileStream(fileName, FileMode.Create))
        {
            BinaryFormatter formatter = new BinaryFormatter();
            formatter.Serialize(fs, collection);
        }
    }

    static SortedDictionary<string, long> Deserializelements(string path)
    {
        SortedDictionary<string, long> elements;
        FileStream fs = new FileStream("collection.bin", FileMode.Open);
          
        BinaryFormatter formatter = new BinaryFormatter();
        elements = (SortedDictionary<string, long>)formatter.Deserialize(fs);
        return elements;
            
        
    }

        static void Main(string[] args)
    {
        string path = @"C:\\Users\\hubci\\OneDrive\\Pulpit\\pg\\Software platforms";
        if (Directory.Exists(path))
        {
            printOut(path, 0);
            DirectoryInfo directoryInfo = new DirectoryInfo(path);
            DateTime oldestFileDate = directoryInfo.FindOldestDate();
            Console.WriteLine($"Najstarszy plik w katalogu: {oldestFileDate}");

            SortedDictionary<string, long> elements = LoadElements(path);
            SerializeCollection(elements, "collection.bin");

            SortedDictionary<string, long> deserialized = Deserializelements("collection.bin");

            DisplayCollection(elements);
        }
    }

}