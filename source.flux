Int integer = 3;
String string = "Test";

List<Int> list = List(1, 2, 3)

type Student = union(id: Int, name: String)
Student element = Student(id: 1, name: "Student Test");

String console = read();
String 1document = read(filename: "source.txt");

print(message: "test", filename: "source.txt"");

if(integer >= 3) {
    print(message: "integer");
} else if(integer <= 2) {
    print(message: "test");
} else {
    print(message: "works");
}

for(Int index : range(low: 1, high: 10)) { }

for(Int element : list) { }

def sum(left: Int, right: Int) -> Int {
    return left + right;
}

def concat(left: String, right: String) -> String {
	return left + right;
}

String expression = concat(left: "Testing", right: "Testing Long String");
Int result = sum(left: 100, right: 20);