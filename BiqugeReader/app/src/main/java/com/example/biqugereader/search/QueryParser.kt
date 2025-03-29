package com.example.biqugereader.search

import com.example.biqugereader.model.Book
import java.util.regex.Pattern

class QueryParser {
    private val rolePattern = Pattern.compile("主角是(.+?)(文|)")
    private val authorPattern = Pattern.compile("作者[：:](.+?)( |$)")
    private val titlePattern = Pattern.compile("《(.+?)》")

    fun parse(query: String): Map<String, String> {
        val criteria = mutableMapOf<String, String>()
        
        // 提取角色信息
        rolePattern.matcher(query).let {
            if (it.find()) {
                criteria["role"] = it.group(1)
            }
        }

        // 提取作者信息
        authorPattern.matcher(query).let {
            if (it.find()) {
                criteria["author"] = it.group(1)
            }
        }

        // 提取书名信息
        titlePattern.matcher(query).let {
            if (it.find()) {
                criteria["title"] = it.group(1)
            }
        }

        // 处理语义标签
        criteria["tags"] = query.split(" ").filter { it.endsWith("文") }.joinToString(",")

        return criteria
    }

    fun match(book: Book, criteria: Map<String, String>): Boolean {
        return criteria.all { (key, value) ->
            when (key) {
                "role" -> book.tags.any { it.contains(value) }
                "author" -> book.author.contains(value)
                "title" -> book.title.contains(value)
                "tags" -> value.split(",").all { tag -> book.tags.any { it.contains(tag) } }
                else -> true
            }
        }
    }
}