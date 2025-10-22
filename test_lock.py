import requests
import time
from concurrent.futures import ThreadPoolExecutor, as_completed

url = "http://127.0.0.1:8080/redis-demo"

data = "{}"

headers = { }


response = requests.request("POST", url + "/test-lock-init", headers=headers, data=data)

print(response)

def run(i):
    print("run %d " % i)
    requests.request("POST", url + "/test-lock3", headers=headers, data=data)
    # time.sleep(0.1)

with ThreadPoolExecutor(max_workers=50) as executor:
    # for i in range(0, 1000):
    #     print("%d start" % i)
    #     executor.submit(run)
    futures = [executor.submit(run, i) for i in range(1000)]
    for future in as_completed(futures):
        pass
    print("complete")