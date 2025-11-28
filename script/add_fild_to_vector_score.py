# -*- coding: utf-8 -*-

import os
import requests
from typing import List, Set


INPUT_FILE = "D:\\Cache\\ESJZone-novel-mirror-main\\index.txt"
STATUS_FILE = "D:\\Cache\\ESJZone-novel-mirror-main\\processed.txt"

API_ENDPOINT = 'http://127.0.0.1:9001/langchain/rag/add' 

TIMEOUT_SECONDS = 900 



def load_paths(file_name: str) -> List[str]:
    try:
        with open(file_name, 'r', encoding='utf-8') as f:
            return [line.strip() for line in f if line.strip()]
    except Exception as e:
        print(f"读取文件 {file_name} 时发生错误: {e}")
        return []

def load_processed_paths(file_name: str) -> Set[str]:
    if not os.path.exists(file_name):
        return set()
    try:
        with open(file_name, 'r', encoding='utf-8') as f:
            return set(line.strip() for line in f if line.strip())
    except Exception as e:
        print(f"加载状态文件 {file_name} 时发生错误: {e}")
        return set()

def mark_as_processed(file_name: str, path: str):
    with open(file_name, 'a', encoding='utf-8') as f:
        f.write(path + '\n')


def call_rag_api(path: str) -> bool:
    print(f"-> 正在请求导入: {path}")
    
    querystring = {"path": path}
    
    session = requests.Session()
    
    try:
        response = session.request(
            "POST", 
            API_ENDPOINT, 
            params=querystring,
            timeout=TIMEOUT_SECONDS 
        )

        response.raise_for_status() 
        print(f"状态码: {response.status_code}")
        return True
    except Exception as e:
        print(f"发生未知错误: {e}")
        return False


def main():
    all_paths = load_paths(INPUT_FILE)
    if not all_paths:
        return

    processed_paths = load_processed_paths(STATUS_FILE)
    to_process = [path for path in all_paths if path not in processed_paths]

    if not to_process:
        print("所有文件路径都已成功处理，无需重复调用。")
        return

    print(f"\n--- 任务概览 ---")
    print(f"总共找到: {len(all_paths)} 个文件")
    print(f"已处理 (将跳过): {len(processed_paths)} 个")
    print(f"本次需要处理: {len(to_process)} 个新文件\n")

    success_count = 0
    failure_count = 0
    
    for path in to_process:
        is_success = call_rag_api(path)
        
        if is_success:
            success_count += 1
            mark_as_processed(STATUS_FILE, path)
        else:
            failure_count += 1
        
        print("-" * 30)

    print("\n--- 任务总结 ---")
    print(f"本次运行成功处理: {success_count} 个")
    print(f"本次运行处理失败: {failure_count} 个")
    print(f"状态文件 {STATUS_FILE} 已更新。")


if __name__ == "__main__":
    main()