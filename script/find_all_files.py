# -*- coding: utf-8 -*-

import os
from typing import List

def find_all_files(root_dir: str) -> List[str]:
    if not os.path.isdir(root_dir):
        print(f"错误: 路径 '{root_dir}' 不是一个有效目录。")
        return []

    all_files = []


    for current_dir, sub_dirs, files in os.walk(root_dir):
        for file_name in files:
            full_path = os.path.join(current_dir, file_name)
            all_files.append(full_path)

    return all_files


target_directory = "D:\\Cache\\ESJZone-novel-mirror-main\\Novel"
output_file_name = "D:\\Cache\\ESJZone-novel-mirror-main\\index.txt"


print(f"目录: {target_directory}\n")

found_files = find_all_files(target_directory)

if found_files:
    print(f"找到 {len(found_files)} 个文件:")
    with open(output_file_name, 'w', encoding='utf-8') as f:
        for i, file_path in enumerate(found_files):
            f.write(f"{file_path}\n")
else:
    print("未找到任何文件或目录无效。")