# Gitlet Design Document

**Name**: Nyx

# Classes and Data Structures
———————————————————
## 对象

* ### blobs
* ### branch
* ### commits
————————————————————
## -Main

### Fields

无

## -Repository

### Fields
1. public static final File CWD = new File(System.getProperty("user.dir"))
2. public static final File GITLET_DIR = join(CWD, ".gitlet")
3. public static final File head = join(GITLET_DIR, "head")
4. public static final File Objects = join(GITLET_DIR, "Objects")
5. public static final File Index = join(GITLET_DIR, "Index")
6. public static final File refs = join(GITLET_DIR, "refs")
7. public static File commits = join(refs, "commits")
8. public static File heads = join(refs, "heads")
9. static index stage = readObject(Index, index.class);
## -Commit
### Fields

1. private String timestamp 
2. private String message 
3. private String parent 
4. private String hash_code 
5. public String [] files
## -blobs

### Fields

1. public static String name 
2. public static String sha_1 
3. public static byte file
## -branch

### Fields

1. public static String name
2. public static Commit head
## -index

### fields
1. public static final File Index = join(GITLET_DIR, "Index");
2. private final Map<String, String> added;
3. private final Set<String> removed;
4. private final Set<String> tracked;

## -Checkout

### Fields

1.

## -merge(最棘手的一个)

### Fields

# Algorithms

### 合并冲突

* Checking if a merge is necessary
* Determining which files (if any) have a conflict.
* Representing the conflict in the file.（how？）

### 打印当前状态（status）

### 打印所有提交的信息(log\global_log)

### 寻找特定提交（find）

### 

# Persistence

### 目录结构如下：
#### CWD                            
└── .GITLET_dir
├── head
├── refs
    ├── commits
    └── heads
├── Index
└── objects-blobs



### The Repository class will set up all the persistence(maybe?)
1. Create the .GITLET_dir folder if it doesn’t already exist 
2. Create the commits folder if it doesn’t already exist 
3. Create the blobs folders if it doesn’t already exist
4. Create the head file if it doesn't already exist

### 这里不存在任何的覆盖问题，感谢git