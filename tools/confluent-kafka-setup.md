# Confluent Kafka

## 📌 Introduction to Confluent Kafka

Confluent Kafka is a robust platform for building **real-time data pipelines** and **streaming applications**.  
It is built on top of **Apache Kafka** and extends its core capabilities by providing additional tools, services, and enterprise-grade features that simplify deployment, monitoring, and scaling.

---

## ⚙️ Installing the Confluent CLI

To begin using Confluent Kafka, the **Confluent CLI** must be installed. The CLI provides a convenient way to manage Confluent resources and services.


| Step | Description | Command | Explanation |
|------|-------------|---------|-------------|
| **1** | Install via Homebrew (macOS) | ```brew install confluentinc/tap/cli``` | Installs the Confluent CLI using Homebrew, the package manager for macOS. |
| **2** | Install via `curl` (alternative method) | ```curl -sL --http1.1 https://cnfl.io/cli \| sh -s -- latest``` | - `curl`: Transfers data with URLs <br> - `-sL`: Silent mode and follows redirects <br> - ```--http1.1`: Ensures HTTP/1.1 protocol```<br> - `\|`: Pipes the output to the shell <br> - `sh`: Executes the script via shell interpreter <br> - ```-s```: Silent mode, suppressing progress output
| **3** | Verify installation | ```confluent version``` | Displays the currently installed Confluent CLI version. <br> ```--latest```: Checks for the latest version available.

---

## 🚀 Setting Up Environment

| Step | Task                          | Command                                                    | What it does                                                                                                                                  | Example                                                        |
| ---- | ----------------------------- | ---------------------------------------------------------- | --------------------------------------------------------------------------------------------------------------------------------------------- | -------------------------------------------------------------- |
| 1    | Log in and persist session    | `confluent login --save`                                   | Authenticates you to Confluent Cloud and saves credentials locally so you are not prompted each time.                                         | `confluent login --save`                                       |
| 2    | List environments             | `confluent environment list`                               | Shows all your Confluent Cloud environments so you can pick the right one. The active environment is the one with the asterisk.                                                                    | `confluent environment list`                                   |
| 3    | Select environment            | `confluent environment use {ENV_ID}`                       | Sets the active environment context for subsequent commands.                                                                                  | `confluent environment use env-abc123`                         |
| 4    | List Kafka clusters           | `confluent kafka cluster list`                             | Lists available Kafka clusters in the selected environment.                                                                                   | `confluent kafka cluster list`                                 |
| 5    | Select Kafka cluster          | `confluent kafka cluster use {CLUSTER_ID}`                 | Sets the active Kafka cluster context.                                                                                                        | `confluent kafka cluster use lkc-9v5r7x`                       |
| 6    | Create API key for a resource | `confluent api-key create --resource {RESOURCE_ID}`        | Creates an API key and secret bound to a specific resource. For Kafka data-plane usage, use the **Kafka cluster ID** (for example `lkc-...`). | `confluent api-key create --resource lkc-9v5r7x`               |
| 7    | Persist API key for use       | `confluent api-key use {API_KEY} --resource {RESOURCE_ID}` | Stores the API key and associates it with the resource for CLI and client defaults.                                                           | `confluent api-key use ABCDEFGHIJKLMNOP --resource lkc-9v5r7x` |

## 📝 End-to-End Example

```bash
# 1) Log in and save the session locally
confluent login --save

# 2) See your environments
confluent environment list

# 3) Set the active environment
confluent environment use env-abc123

# 4) List Kafka clusters in the selected environment
confluent kafka cluster list

# 5) Set the active Kafka cluster
confluent kafka cluster use lkc-9v5r7x

# 6) Create an API key for that Kafka cluster
#    IMPORTANT: copy the printed API key and secret somewhere secure
confluent api-key create --resource lkc-9v5r7x

# 7) Persist the API key for this cluster so CLI and clients can use it
confluent api-key use ABCDEFGHIJKLMNOP --resource lkc-9v5r7x
```

### ✍️ Producers and Consumers

| Step | Task                                 | Command                                                                                | What it does                                                                                                                  | Example                                                                                          |
| ---- | ------------------------------------ | -------------------------------------------------------------------------------------- | ----------------------------------------------------------------------------------------------------------------------------- | ------------------------------------------------------------------------------------------------ |
| 1    | List topics                          | `confluent kafka topic list`                                                           | Shows available topics in the **active cluster**.                                                                             | `confluent kafka topic list`                                                                     |
| 2    | Create a topic (if needed)           | `confluent kafka topic create {TOPIC} --partitions {N} --config cleanup.policy=delete` | Creates a topic with the given partitions and common defaults.                                                                | `confluent kafka topic create thermostat_readings --partitions 3 --config cleanup.policy=delete` |
| 3    | Produce keyed JSON messages          | `confluent kafka topic produce {TOPIC} --parse-key --delimiter ':'`                    | Opens an interactive producer. Interprets text before `:` as the **key** and after as the **value**. The `--parse-key` flag automatically reads both the key and the value separated by the ":" symbol. End input with `Ctrl+D` or `Ctrl+C`. | `confluent kafka topic produce thermostat_readings --parse-key`                  |
| 4    | Consume from the beginning with keys | `confluent kafka topic consume {TOPIC} --from-beginning --print-key`   | Reads all messages from the earliest offset and prints the key alongside the value.                                           | `confluent kafka topic consume thermostat_readings --from-beginning --print-key` |
| 5    | Tail new messages only               | `confluent kafka topic consume {TOPIC}`                                                | Streams new messages arriving after the command starts.                                                                       | `confluent kafka topic consume thermostat_readings`                                              |
| 6    | Describe a topic                     | `confluent kafka topic describe {TOPIC}`                                               | Shows partitions, replication, configs and retention.                                                                         | `confluent kafka topic describe thermostat_readings`                                             |



705:{"sensor_id":705,"location":"thai's bathroom","temperature":10,"read_at":1736522049}